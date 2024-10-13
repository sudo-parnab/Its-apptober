package com.example.taskmanager.di

import com.example.taskmanager.data.ItemsRepository
import com.example.taskmanager.data.OfflineItemsRepository
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.viewmodel.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val repoModule = module{
    single {
        TaskDatabase.getDatabase(androidContext()).itemDao()
    }
    single<ItemsRepository> {
        OfflineItemsRepository(get())
    }
    viewModel{
        TaskViewModel(get())
    }
}