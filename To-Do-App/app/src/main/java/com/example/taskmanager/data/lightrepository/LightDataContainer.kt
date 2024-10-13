package com.example.taskmanager.data.lightrepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface AppContainer {
    val lightDataContainer: LightRepository
}

class LightDataContainer(dataStore: DataStore<Preferences>): AppContainer  {
    override val lightDataContainer: LightRepository = LightRepository(dataStore)
}