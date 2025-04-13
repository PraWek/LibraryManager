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
                // Use ViewModel that will survive configuration changes
                val viewModel: LibraryViewModel = viewModel()
                val items = viewModel.items
                val showTypeDialog = viewModel.showTypeDialog
                val selectedType = viewModel.selectedType

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { viewModel.toggleTypeDialog(true) }) {
                            Icon(Icons.Default.Add, contentDescription = "Добавить")
                        }
                    }
                ) { innerPadding ->

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
                        items(items, key = { "${it.id}-${it.available}" }) { item ->
                            LibraryCard(
                                item = item,
                                onItemClick = { clickedItem ->
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
