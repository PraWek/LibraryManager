package com.example.librarymanager.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.library.Book
import com.example.library.Newspaper
import com.example.library.Disc
import com.example.library.LibraryItem

@Composable
fun ItemTypeDialog(
    onDismiss: () -> Unit,
    onTypeSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите тип элемента") },
        confirmButton = {},
        dismissButton = { Button(onClick = onDismiss) { Text("Отмена") } },
        text = {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onTypeSelected("книга") }) {
                    Text("Книга")
                }
                Button(onClick = { onTypeSelected("газета") }) {
                    Text("Газета")
                }
                Button(onClick = { onTypeSelected("диск") }) {
                    Text("Диск")
                }
            }
        }
    )
}

@Composable
fun AddItemDialog(
    type: String,
    nextId: Int,
    onDismiss: () -> Unit,
    onItemCreated: (LibraryItem) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var additionalInfo by remember { mutableStateOf("") }
    var additionalInfo2 by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Создать ${type}") },
        confirmButton = {
            Button(
                onClick = {
                    val item = when (type) {
                        "книга" -> Book(nextId, true, name, additionalInfo.toIntOrNull() ?: 0, additionalInfo2)
                        "газета" -> Newspaper(nextId, true, name, additionalInfo.toIntOrNull() ?: 2024, additionalInfo2.toIntOrNull() ?: 1)
                        "диск" -> Disc(nextId, true, name, additionalInfo)
                        else -> null
                    }
                    item?.let { onItemCreated(it) }
                },
                enabled = name.isNotBlank()
            ) {
                Text("Создать")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название") }
                )
                when (type) {
                    "книга" -> {
                        TextField(
                            value = additionalInfo,
                            onValueChange = { additionalInfo = it },
                            label = { Text("Количество страниц") }
                        )
                        TextField(
                            value = additionalInfo2,
                            onValueChange = { additionalInfo2 = it },
                            label = { Text("Автор") }
                        )
                    }
                    "газета" -> {
                        TextField(
                            value = additionalInfo,
                            onValueChange = { additionalInfo = it },
                            label = { Text("Год") }
                        )
                        TextField(
                            value = additionalInfo2,
                            onValueChange = { additionalInfo2 = it },
                            label = { Text("Номер") }
                        )
                    }
                    "диск" -> {
                        TextField(
                            value = additionalInfo,
                            onValueChange = { additionalInfo = it },
                            label = { Text("Тип (DVD/CD)") }
                        )
                    }
                }
            }
        }
    )
}
