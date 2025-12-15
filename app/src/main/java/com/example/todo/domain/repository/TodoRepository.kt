package com.example.todo.domain.repository

import androidx.paging.PagingData
import com.example.todo.domain.model.TodoModel
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodosPaged(): Flow<PagingData<TodoModel>>

    suspend fun getTodoById(id: Int): TodoModel?

    suspend fun insertTodo(todo: TodoModel)

    suspend fun updateTodo(todo: TodoModel)

    suspend fun deleteTodo(todo: TodoModel)
}