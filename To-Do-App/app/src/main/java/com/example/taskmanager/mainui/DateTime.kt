package com.example.taskmanager.mainui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDateTime(reminderDate: MutableState<String>, showDate: MutableState<Boolean>, reminderTime: MutableState<String>, showTime: MutableState<Boolean>, timeInMillis: MutableState<Long>) {

    val dateState = rememberDatePickerState(Date().time) // Date().time will show the current date
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    if (showDate.value) {
        DatePickerDialog(
            onDismissRequest = {
                showDate.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDate.value = false
                    reminderDate.value = dateState.selectedDateMillis?.let { convert(it) } ?: ""
                }) {
                    Text("Set Date")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDate.value = false
                }) {
                    Text("Cancel")
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            DatePicker(state = dateState)
        }
    }

    if (showTime.value) {
        TimePickerDialog(
            onDismiss = { showTime.value = false },
            onConfirm = {
                showTime.value = false
                reminderTime.value = "${timePickerState.hour}:${timePickerState.minute}"
            },
            reminderTime,
            timePickerState
        ) {
            TimePicker(state = timePickerState)
        }
    }
    timeInMillis.value = convertDateAndTimeToMilliseconds(dateState, timePickerState)
}

private fun convert(date: Long): String {
    val dateNew = Date(date) // converts the long to date object
    val formatter = SimpleDateFormat.getDateInstance() //Creates a date formatter that uses the default date and time format for the current locale.

    return formatter.format(dateNew) // converts the  Date object to a string
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, reminderTime: MutableState<String>, timePickerState: TimePickerState, content: @Composable () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        text = { content() },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                reminderTime.value = "${timePickerState.hour}:${timePickerState.minute}"
                onConfirm()
            }) {
                Text("Set Time")
            }
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
fun convertDateAndTimeToMilliseconds(dateState: DatePickerState, timePickerState: TimePickerState): Long {
    val selectedDateMillis = dateState.selectedDateMillis
    val selectedDate = selectedDateMillis?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }
    val calendar = Calendar.getInstance()

    selectedDate?.let {
        calendar.set(
            it.year,
            it.monthValue - 1, // Calendar months are zero-based
            it.dayOfMonth,
            timePickerState.hour,
            timePickerState.minute
        )
    }

    return calendar.timeInMillis
}
