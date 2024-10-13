package com.example.taskmanager.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemsDao: ItemDao) : ItemsRepository {

    override suspend fun insertItem(item: Item):Long = itemsDao.insert(item)

    override fun getAllItem(): Flow<List<Item>> = itemsDao.getItems()

    override suspend fun updateItem(item: Item) = itemsDao.update(item)

    override suspend fun deleteItem(item: Item) = itemsDao.delete(item)

    override fun getReminderTime(id:Long): Long = itemsDao.getReminderTime(id)

}