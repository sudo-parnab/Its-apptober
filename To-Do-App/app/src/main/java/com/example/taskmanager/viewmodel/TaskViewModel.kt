package com.example.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskmanager.TaskApplication
import com.example.taskmanager.data.Item
import com.example.taskmanager.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: ItemsRepository): ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items


    init{
        viewModelScope.launch{
            repository.getAllItem().collect{ items ->
                _items.value = items
            }
        }
    }

    suspend fun insertItem(item: Item):Long{
         return repository.insertItem(item)
    }

    suspend fun deleteItem(item: Item){
        repository.deleteItem(item)
    }

    suspend fun updateItem(item: Item){
        repository.updateItem(item)
    }

    fun getReminder(id:Long):Long{
       return repository.getReminderTime(id)
    }
}