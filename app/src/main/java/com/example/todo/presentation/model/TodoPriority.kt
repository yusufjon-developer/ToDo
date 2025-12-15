package com.example.todo.presentation.model

import androidx.compose.ui.graphics.Color

enum class TodoPriority(val value: Int, val label: String, val color: Color) {
    LOW(0, "Низкий", Color(0xFF8BC34A)),
    MEDIUM(1, "Средний", Color(0xFFFFC107)),
    HIGH(2, "Высокий", Color(0xFFF44336));

    companion object {
        fun fromInt(value: Int) = entries.find { it.value == value } ?: LOW
    }
}