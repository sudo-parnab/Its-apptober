package com.example.todolistapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {
    val todoDao = MainApplication.todoDatabase.getTodoDao()
    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()

    fun addTodo(title: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(
                Todo(
                    title = title,
                    description = desc,
                    createdAt = Date.from(Instant.now())
                )
            )
        }
    }

    fun editTodo(id: Int, title: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.editTodo(id, title, desc)
        }
    }

    fun copyTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.copyTodo(id)
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }
}