package com.example.stopwatchapp

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.stopwatchapp.service.StopwatchService
import com.example.stopwatchapp.ui.theme.StopwatchAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isBound by mutableStateOf(false)
    private lateinit var stopwatchService: StopwatchService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as StopwatchService.StopwatchBinder
            stopwatchService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StopwatchService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchAppTheme {
                if (isBound) {
                    Stopwatch(stopwatchService = stopwatchService, timeStamps = stopwatchService.timeStampList.value)
                }
            }
        }
        requestPermissions(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun requestPermissions(vararg permissions: String) {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            result.entries.forEach {
                Log.d("MainActivity", "${it.key} = ${it.value}")
            }
        }
        requestPermissionLauncher.launch(permissions.asList().toTypedArray())
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}
