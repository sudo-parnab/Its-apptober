package com.example.stopwatchapp.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_CANCEL
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_START
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_STOP
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_TIMESTAMP
import com.example.stopwatchapp.util.Constants.NOTIFICATION_CHANNEL_ID
import com.example.stopwatchapp.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.stopwatchapp.util.Constants.NOTIFICATION_ID
import com.example.stopwatchapp.util.Constants.STOPWATCH_STATE
import com.example.stopwatchapp.util.formatTime
import com.example.stopwatchapp.util.pad
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
@AndroidEntryPoint
class StopwatchService : Service() {
    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    private val binder = StopwatchBinder()

    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer

    var milliseconds = mutableStateOf("00")
        private set
    var seconds = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("00")
        private set

    var currentState = mutableStateOf(StopwatchState.Idle)
        private set
    var timeStampList = binder.timeStampList
        private set

    override fun onBind(p0: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.Started.name -> {
                setStopButton()
                startForegroundService()
                startStopwatch { minutes, seconds, _ ->
                    updateNotification(minutes = minutes, seconds = seconds)
                }
            }
            StopwatchState.Stopped.name -> {
                stopStopwatch()
                setResumeButton()
            }
            StopwatchState.Canceled.name -> {
                stopStopwatch()
                cancelStopwatch()
                stopForegroundService()
            }
        }
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    setStopButton()
                    startForegroundService()
                    startStopwatch { minutes, seconds, _ ->
                        updateNotification(minutes = minutes, seconds = seconds)
                    }
                }
                ACTION_SERVICE_STOP -> {
                    stopStopwatch()
                    setResumeButton()
                }
                ACTION_SERVICE_CANCEL -> {
                    stopStopwatch()
                    cancelStopwatch()
                    stopForegroundService()
                }
                ACTION_SERVICE_TIMESTAMP -> {
                    addTimeStamp()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startStopwatch(onTick: (m: String, s: String, ms: String) -> Unit) {
        currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 10L, period = 10L) {
            duration = duration.plus(10.milliseconds)
            updateTimeUnits()
            onTick(minutes.value, seconds.value, milliseconds.value)
        }
    }

    private fun updateTimeUnits() {
        duration.toComponents { _, minutes, seconds, milliseconds ->
            this@StopwatchService.minutes.value = minutes.pad()
            this@StopwatchService.seconds.value = seconds.pad()
            this@StopwatchService.milliseconds.value = if (milliseconds.toString()=="0") "00" else milliseconds.toString().take(2)
        }
    }

    private fun stopStopwatch() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        currentState.value = StopwatchState.Stopped
    }

    private fun cancelStopwatch() {
        duration = Duration.ZERO
        currentState.value = StopwatchState.Idle
        updateTimeUnits()
        timeStampList.value = emptyList()
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("NewApi")
    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun updateNotification(minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText(
                formatTime(minutes = minutes, seconds = seconds)
            ).build()
        )
    }

    private fun addTimeStamp() {
        timeStampList.value += TimeStamp(
            timeStamp = duration
        )
    }

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalAnimationApi::class)
    private fun setStopButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Stop",
                ServiceHelper.stopPendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalAnimationApi::class)
    private fun setResumeButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Resume",
                ServiceHelper.resumePendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
        var timeStampList = mutableStateOf(emptyList<TimeStamp>())
            private set
    }
}

enum class StopwatchState {
    Idle,
    Started,
    Stopped,
    Canceled
}

data class TimeStamp (
    val timeStamp: Duration
)