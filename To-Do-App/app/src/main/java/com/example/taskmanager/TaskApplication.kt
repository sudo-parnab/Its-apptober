package com.example.taskmanager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanager.data.lightrepository.LightDataContainer
import com.example.taskmanager.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

    private const val PREFERENCE_NAME = "ScreenLightMode"
    private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class TaskApplication: Application() {
    lateinit var userPreference: LightDataContainer

    override fun onCreate(){
        super.onCreate()
        userPreference = LightDataContainer(datastore)
        startKoin{
            androidContext(this@TaskApplication)
            modules(repoModule)
        }
    }

}