package com.example.librarymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.library.Book
import com.example.library.Disc
import com.example.librarymanager.ui.components.ItemTypeDialog
import com.example.librarymanager.ui.components.AddItemDialog
import com.example.library.Newspaper
import com.example.librarymanager.ui.components.LibraryCard
import com.example.librarymanager.ui.theme.LibraryManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryManagerTheme {
                val viewModel: LibraryViewModel = viewModel()
                viewModel.initDataStore(this)
                val items = viewModel.items
                val showTypeDialog = viewModel.showTypeDialog
                val selectedType = viewModel.selectedType

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { viewModel.setGoogleBooksMode(false) }) {
                                Text("Библиотека")
                            }
                            Button(onClick = { viewModel.setGoogleBooksMode(true) }) {
                                Text("Google Books")
                            }
                        }
                    },
                    floatingActionButton = {
                        if (!uiState.value.isGoogleBooksMode) {
                            FloatingActionButton(onClick = { viewModel.toggleTypeDialog(true) }) {
                                Icon(Icons.Default.Add, contentDescription = "Добавить")
                            }
                        }
                    }
                ) { innerPadding ->
                    if (uiState.value.isGoogleBooksMode) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = uiState.value.searchAuthor,
                                onValueChange = { viewModel.updateSearchAuthor(it) },
                                label = { Text("Author") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = uiState.value.searchTitle,
                                onValueChange = { viewModel.updateSearchTitle(it) },
                                label = { Text("Title") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.searchGoogleBooks() },
                                enabled = uiState.value.searchAuthor.length >= 3 ||
                                        uiState.value.searchTitle.length >= 3,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Search")
                            }

                            if (uiState.value.isLoading) {
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }

                            if (uiState.value.error != null) {
                                Text(
                                    text = uiState.value.error!!,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    if (showTypeDialog) {
                        ItemTypeDialog(
                            onDismiss = { viewModel.toggleTypeDialog(false) },
                            onTypeSelected = { type ->
                                viewModel.updateSelectedType(type)
                                viewModel.toggleTypeDialog(false)
                            }
                        )
                    }

                    selectedType?.let { type ->
                        AddItemDialog(
                            type = type,
                            nextId = items.maxOf { it.id } + 1,
                            onDismiss = { viewModel.updateSelectedType(null) },
                            onItemCreated = { newItem ->
                                viewModel.addItem(newItem)
                            }
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        items(viewModel.uiState.value.items, key = { item -> "${item.id}" }) { item ->
                            LibraryCard(
                                item = item,
                                onClick = { clickedItem ->
                                    val itemType = when(clickedItem) {
                                        is Book -> "Книга"
                                        is Newspaper -> "Газета"
                                        is Disc -> "Диск"
                                        else -> "Неизвестный тип"
                                    }
                                    val intent = DetailActivity.createIntent(
                                        this@MainActivity,
                                        clickedItem.id,
                                        clickedItem.name,
                                        itemType,
                                        clickedItem.getDetailedInfo()
                                    )
                                    startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
