package com.example.todo.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.todo.data.local.entity.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY isCompleted ASC, priority DESC, createdAt DESC")
    fun getTodosPaged(): PagingSource<Int, TodoEntity>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
}