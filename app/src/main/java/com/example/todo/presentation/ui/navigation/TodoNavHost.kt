package com.example.todo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.presentation.ui.screens.add.AddTodoScreen
import com.example.todo.presentation.ui.screens.detail.DetailTodoScreen
import com.example.todo.presentation.ui.screens.edit.EditTodoScreen
import com.example.todo.presentation.ui.screens.list.TodoListScreen

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {

        composable(Screen.List.route) {
            TodoListScreen(
                onAddClick = {
                    navController.navigate(Screen.Add.route)
                },
                onItemClick = { todo ->
                    navController.navigate(Screen.Detail.createRoute(todo.id))
                }
            )
        }

        composable(Screen.Add.route) {
            AddTodoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.Detail.ARG_TODO_ID) { type = NavType.IntType }
            )
        ) {
            DetailTodoScreen(
                onBack = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate(Screen.Edit.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.Edit.route,
            arguments = listOf(
                navArgument(Screen.Edit.ARG_TODO_ID) { type = NavType.IntType }
            )
        ) {
            EditTodoScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}