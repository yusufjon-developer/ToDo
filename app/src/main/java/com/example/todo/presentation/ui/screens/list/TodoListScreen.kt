package com.example.todo.presentation.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.todo.presentation.ui.components.TodoItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onAddClick: () -> Unit,
    onItemClick: (com.example.todo.domain.model.TodoModel) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val todos = viewModel.todos.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои Задачи", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = todos.itemCount,
                key = todos.itemKey { it.id }
            ) { index ->
                val todo = todos[index]
                if (todo != null) {
                    TodoItemCard(
                        todo = todo,
                        onChecked = { viewModel.onTodoChecked(todo) },
                        onDelete = { viewModel.onDeleteTodo(todo) },
                        onClick = { onItemClick(todo) }
                    )
                }
            }

            when (todos.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Ошибка загрузки",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                else -> {}
            }
        }
    }
}