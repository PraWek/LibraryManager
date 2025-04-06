package com.example.librarymanager

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.library.Book
import com.example.library.Disc
import com.example.librarymanager.ui.components.ItemTypeDialog
import com.example.librarymanager.ui.components.AddItemDialog
import com.example.library.LibraryManager
import com.example.library.Newspaper
import com.example.librarymanager.ui.components.LibraryCard
import com.example.librarymanager.ui.theme.LibraryManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryManagerTheme {
                val libraryManager = remember { LibraryManager() }
                var items by remember { mutableStateOf(libraryManager.getAllItems()) }
                var showTypeDialog by remember { mutableStateOf(false) }
                var selectedType by remember { mutableStateOf<String?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { showTypeDialog = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Добавить")
                        }
                    }
                ) { innerPadding ->

                    if (showTypeDialog) {
                        ItemTypeDialog(
                            onDismiss = { showTypeDialog = false },
                            onTypeSelected = { type ->
                                selectedType = type
                                showTypeDialog = false
                            }
                        )
                    }

                    selectedType?.let { type ->
                        AddItemDialog(
                            type = type,
                            nextId = items.maxOf { it.id } + 1,
                            onDismiss = { selectedType = null },
                            onItemCreated = { newItem ->
                                items = items + newItem
                                selectedType = null
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
                                    val intent = android.content.Intent(this@MainActivity, DetailActivity::class.java).apply {
                                        putExtra("ITEM_ID", clickedItem.id)
                                        putExtra("ITEM_NAME", clickedItem.name)
                                        putExtra("ITEM_TYPE", when(clickedItem) {
                                            is Book -> "Книга"
                                            is Newspaper -> "Газета"
                                            is Disc -> "Диск"
                                            else -> "Неизвестный тип"
                                        })
                                        putExtra("DETAILED_INFO", clickedItem.getDetailedInfo())
                                    }
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
