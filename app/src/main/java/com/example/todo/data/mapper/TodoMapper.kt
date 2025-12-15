package com.example.todo.data.mapper

import com.example.todo.data.local.entity.TodoEntity
import com.example.todo.domain.model.TodoModel

fun TodoEntity.toDomain(): TodoModel {
    return TodoModel(
        id = id,
        title = title,
        description = description,
        priority = priority,
        isCompleted = isCompleted,
        createdAt = createdAt,
        completedAt = completedAt
    )
}

fun TodoModel.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        description = description,
        priority = priority,
        isCompleted = isCompleted,
        createdAt = createdAt,
        completedAt = completedAt
    )
}

fun List<TodoEntity>.toDomainList(): List<TodoModel> = map { it.toDomain() }