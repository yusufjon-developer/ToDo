package com.example.todo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.todo.data.local.dao.TodoDao
import com.example.todo.data.mapper.toDomain
import com.example.todo.data.mapper.toEntity
import com.example.todo.domain.model.TodoModel
import com.example.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : TodoRepository {

    override fun getTodosPaged(): Flow<PagingData<TodoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { dao.getTodosPaged() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun insertTodo(todo: TodoModel) {
        dao.insertTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: TodoModel) {
        dao.updateTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: TodoModel) {
        dao.deleteTodo(todo.toEntity())
    }
}