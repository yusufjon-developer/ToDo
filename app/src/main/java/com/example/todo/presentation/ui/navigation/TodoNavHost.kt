package com.example.todo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.presentation.ui.screens.add.AddTodoScreen
import com.example.todo.presentation.ui.screens.detail.DetailTodoScreen // Импорт
import com.example.todo.presentation.ui.screens.edit.EditTodoScreen
import com.example.todo.presentation.ui.screens.list.TodoListScreen

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            TodoListScreen(
                onAddClick = { navController.navigate("add") },
                onItemClick = { todo ->
                    navController.navigate("detail/${todo.id}")
                }
            )
        }

        composable("add") {
            AddTodoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "detail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) {
            DetailTodoScreen(
                onBack = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate("edit/$id")
                }
            )
        }

        composable(
            route = "edit/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) {
            EditTodoScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}