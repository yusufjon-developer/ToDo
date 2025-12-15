package com.example.todo.presentation.ui.screens.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.repository.TodoRepository
import com.example.todo.presentation.model.TodoPriority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId: Int = checkNotNull(savedStateHandle["todoId"])

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _priority = MutableStateFlow(TodoPriority.LOW)
    val priority: StateFlow<TodoPriority> = _priority.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var originalCreatedAt: Long = System.currentTimeMillis()
    private var originalIsCompleted: Boolean = false
    private var originalCompletedAt: Long? = null

    init {
        loadTodo()
    }

    private fun loadTodo() {
        viewModelScope.launch {
            val todo = repository.getTodoById(todoId)
            if (todo != null) {
                _title.value = todo.title
                _description.value = todo.description ?: ""
                _priority.value = TodoPriority.fromInt(todo.priority)

                originalCreatedAt = todo.createdAt
                originalIsCompleted = todo.isCompleted
                originalCompletedAt = todo.completedAt
            }
            _isLoading.value = false
        }
    }

    fun onTitleChange(newTitle: String) { _title.value = newTitle }
    fun onDescriptionChange(newDesc: String) { _description.value = newDesc }
    fun onPriorityChange(newPriority: TodoPriority) { _priority.value = newPriority }

    fun updateTodo(onSuccess: () -> Unit) {
        if (_title.value.isBlank()) return

        viewModelScope.launch {
            val updatedTodo = com.example.todo.domain.model.TodoModel(
                id = todoId,
                title = _title.value,
                description = _description.value.ifBlank { null },
                priority = _priority.value.value,
                isCompleted = originalIsCompleted,
                createdAt = originalCreatedAt,
                completedAt = originalCompletedAt
            )
            repository.updateTodo(updatedTodo)
            onSuccess()
        }
    }
}