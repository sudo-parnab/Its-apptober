package com.example.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.taskmanager.TaskApplication
//import com.example.taskmanager.data.worker.ReminderWorker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

/*
class ReminderViewModel(application: Application): ViewModel() {

    private val workManager =  WorkManager.getInstance(application)
    //The code initializes a WorkManager instance using the application context.

    fun updateReminder(dateString: String, timeString: String){

        val reminderTimeMillis = convertToMillis(dateString, timeString)
        val delayDuration = reminderTimeMillis - System.currentTimeMillis()
        val data = workDataOf("reminderTime" to reminderTimeMillis)

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(data) // this is the input data to be extracted from the worker in the worker class
            .setInitialDelay(delayDuration, TimeUnit.MILLISECONDS)// to start the work after the delay duration
            .build()

        workManager.enqueue(workRequest)
    }

    private fun convertToMillis(dateString: String, timeString: String): Long {
        val dateTimeString = "$dateString $timeString"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(dateTimeString)
        return date?.time ?: 0L
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication
                ReminderViewModel(application)
            }
        }
    }
}*/