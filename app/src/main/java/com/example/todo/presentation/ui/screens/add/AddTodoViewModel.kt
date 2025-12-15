package com.example.todo.presentation.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.model.TodoModel
import com.example.todo.domain.repository.TodoRepository
import com.example.todo.presentation.model.TodoPriority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _priority = MutableStateFlow(TodoPriority.LOW)
    val priority: StateFlow<TodoPriority> = _priority.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onDescriptionChange(newDesc: String) {
        _description.value = newDesc
    }

    fun onPriorityChange(newPriority: TodoPriority) {
        _priority.value = newPriority
    }

    fun saveTodo(onSuccess: () -> Unit) {
        if (_title.value.isBlank()) return

        val newTodo = TodoModel(
            title = _title.value,
            description = _description.value.ifBlank { null },
            priority = _priority.value.value,
            createdAt = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.insertTodo(newTodo)
            onSuccess()
        }
    }
}