package com.example.todo.domain.model

data class TodoModel(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Int = 0,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)