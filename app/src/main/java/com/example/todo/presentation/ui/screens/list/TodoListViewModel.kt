package com.example.todo.presentation.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todo.domain.model.TodoModel
import com.example.todo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todos: Flow<PagingData<TodoModel>> = repository.getTodosPaged()
        .cachedIn(viewModelScope)

    fun onTodoChecked(todo: TodoModel) {
        val newStatus = !todo.isCompleted
        val completionTime = if (newStatus) System.currentTimeMillis() else null

        val updatedTodo = todo.copy(
            isCompleted = newStatus,
            completedAt = completionTime
        )

        viewModelScope.launch {
            repository.updateTodo(updatedTodo)
        }
    }

    fun onDeleteTodo(todo: TodoModel) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }
}