package com.example.todo.presentation.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.model.TodoModel
import com.example.todo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId: Int = checkNotNull(savedStateHandle["todoId"])

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadTodo() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            val todo = repository.getTodoById(todoId)
            if (todo != null) {
                _uiState.value = DetailUiState.Success(todo)
            } else {
                _uiState.value = DetailUiState.Error
            }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    object Error : DetailUiState()
    data class Success(val todo: TodoModel) : DetailUiState()
}