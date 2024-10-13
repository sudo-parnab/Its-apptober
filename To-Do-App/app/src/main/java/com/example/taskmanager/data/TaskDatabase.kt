package com.example.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 4, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object{

        @Volatile
        private var Instance: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase{

            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,TaskDatabase::class.java,"items_database")
                 .fallbackToDestructiveMigration()
                    .build()
                    .also{Instance=it}
            }
        }
    }
}