package com.example.todo.presentation.ui.navigation

sealed class Screen(val route: String) {

    object List : Screen("list")
    object Add : Screen("add")

    object Detail : Screen("detail/{todoId}") {
        const val ARG_TODO_ID = "todoId"

        fun createRoute(todoId: Int) = "detail/$todoId"
    }

    object Edit : Screen("edit/{todoId}") {
        const val ARG_TODO_ID = "todoId"

        fun createRoute(todoId: Int) = "edit/$todoId"
    }
}