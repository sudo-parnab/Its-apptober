package com.example.stopwatchapp.util

fun formatTime(minutes: String, seconds: String): String {
    return "$minutes:$seconds"
}
fun formatTime(minutes: String, seconds: String, milliseconds: String): String {
    return "$minutes:$seconds:$milliseconds"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}