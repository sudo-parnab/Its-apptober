package alarmmanager

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.taskmanager.R

class TaskReminderReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TaskReminderReceiver", "onReceive called")
        val taskId = intent?.getLongExtra("TASK_ID", -1) ?: -1

        Log.d("TaskReminderReceiver", "Task ID: $taskId")

        if (taskId == -1L) {
            Log.e("TaskReminderReceiver", "Invalid Task ID")
            return
        }

        val notificationManager = NotificationManagerCompat.from(context!!)
        createNotificationChannel(notificationManager, context)

        val notification = NotificationCompat.Builder(context, "TASK_REMINDER_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task Reminder")
            .setContentText("You have a task to complete!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                context as Activity, // Ensure this is called within an Activity context
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }
        notificationManager.notify(taskId.toInt(), notification)
    }

    private fun createNotificationChannel(manager: NotificationManagerCompat, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "TASK_REMINDER_CHANNEL",
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for task reminders"
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleTaskReminder(context: Context, taskId: Int, reminderTime: Long) {
        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            action = "com.example.taskmanager.ACTION_RECEIVE_REMINDER"
            putExtra("TASK_ID", taskId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            reminderTime,
            pendingIntent
        )
        Log.d("TaskReminderReceiver", "Alarm set for taskId: $taskId at time: $reminderTime")
    }
}
