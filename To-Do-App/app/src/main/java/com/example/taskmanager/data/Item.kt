package com.example.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    var task : String,
    var reminderDate : String,
    var reminderTime : String,
    var timeMillis: Long
)
