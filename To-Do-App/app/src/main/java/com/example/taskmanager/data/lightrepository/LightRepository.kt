package com.example.taskmanager.data.lightrepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LightRepository(private val datastore: DataStore<Preferences>) {

    companion object{
        val USER_SCREEN_MODE = booleanPreferencesKey("screen_mode")
    }

    val access_mode: Flow<Boolean> = datastore.data
        .map{
            preferences->
            preferences[USER_SCREEN_MODE] ?:false
        }

    suspend fun saveMode(mode:Boolean){
        datastore.edit{ preferences->
            preferences[USER_SCREEN_MODE] = mode
        }
    }

}