package com.example.todo.presentation.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.presentation.model.TodoPriority
import com.example.todo.presentation.ui.components.PriorityBadge
import com.example.todo.presentation.utils.formatTimestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTodoScreen(
    onBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: DetailTodoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTodo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали задачи") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state is DetailUiState.Success) {
                        val todo = (state as DetailUiState.Success).todo
                        IconButton(onClick = { onEditClick(todo.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val currentState = state) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DetailUiState.Error -> {
                    Text("Задача не найдена", modifier = Modifier.align(Alignment.Center))
                }
                is DetailUiState.Success -> {
                    val todo = currentState.todo
                    val priority = TodoPriority.fromInt(todo.priority)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PriorityBadge(priority)
                            if (todo.isCompleted) {
                                Badge(containerColor = MaterialTheme.colorScheme.primary) {
                                    Text("Выполнено", modifier = Modifier.padding(4.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = todo.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Создано: ${formatTimestamp(todo.createdAt)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )

                        if (todo.completedAt != null) {
                            Text(
                                text = "Завершено: ${formatTimestamp(todo.completedAt)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Описание",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (!todo.description.isNullOrBlank()) {
                            Text(
                                text = todo.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            Text(
                                text = "Нет описания",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }
}