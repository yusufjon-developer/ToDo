package com.example.todo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.presentation.ui.screens.add.AddTodoScreen
import com.example.todo.presentation.ui.screens.list.TodoListScreen

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            TodoListScreen(
                onAddClick = { navController.navigate("add") }
            )
        }

        composable("add") {
            AddTodoScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}