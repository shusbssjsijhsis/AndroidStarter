package com.atlas.android.viewmodel

import androidx.lifecycle.ViewModel
import com.atlas.android.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodoViewModel : ViewModel() {

    // 内部可修改的状态
    private val _uiState = MutableStateFlow<List<TodoItem>>(emptyList())
    // 暴露给 UI 的只读状态
    val uiState: StateFlow<List<TodoItem>> = _uiState.asStateFlow()

    fun addTodo(text: String) {
        if (text.isBlank()) return
        _uiState.update { currentList ->
            // 新增项放在最前面
            listOf(TodoItem(text = text)) + currentList
        }
    }

    fun toggleTodo(id: String) {
        _uiState.update { currentList ->
            currentList.map { item ->
                if (item.id == id) item.copy(isDone = !item.isDone) else item
            }
        }
    }

    fun removeTodo(id: String) {
        _uiState.update { currentList ->
            currentList.filter { it.id != id }
        }
    }
}
