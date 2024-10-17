package com.example.todolistapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolistapp.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM TODO")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    @Query("UPDATE TODO SET title = :title, description= :desc WHERE id = :id")
    fun editTodo(id: Int, title: String, desc: String)

    @Query("SELECT * FROM TODO WHERE id = :id")
    fun copyTodo(id: Int) : LiveData<Todo>

    @Query("DELETE FROM TODO WHERE id = :id")
    fun deleteTodo(id: Int)
}