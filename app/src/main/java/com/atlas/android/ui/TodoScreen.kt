package com.atlas.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atlas.android.model.TodoItem
import com.atlas.android.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel()
) {
    val todoList by viewModel.uiState.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Atlas Todo (MVVM)",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("What needs to be done?") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(items = todoList, key = { it.id }) { item ->
                TodoItemRow(
                    item = item,
                    onToggle = { viewModel.toggleTodo(item.id) },
                    onDelete = { viewModel.removeTodo(item.id) }
                )
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
fun TodoItemRow(item: TodoItem, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onToggle() }.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (item.isDone) "✅ ${item.text}" else "⬜ ${item.text}",
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = if (item.isDone) TextDecoration.LineThrough else null,
                color = if (item.isDone) Color.Gray else Color.Unspecified
            ),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
        }
    }
}
