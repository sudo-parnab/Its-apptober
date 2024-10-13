package com.example.taskmanager.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    suspend fun insertItem(item: Item): Long

    fun getAllItem(): Flow<List<Item>>

    suspend fun updateItem(item: Item)

    suspend fun deleteItem(item: Item)

     fun getReminderTime(id: Long): Long
}