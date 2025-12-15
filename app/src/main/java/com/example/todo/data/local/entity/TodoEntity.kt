package com.example.todo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Int = 0,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val completedAt: Long? = null
)