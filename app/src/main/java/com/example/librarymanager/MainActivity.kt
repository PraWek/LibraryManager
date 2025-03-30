
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.library.LibraryManager
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

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        items(items, key = { "${it.id}-${it.available}" }) { item ->
                            LibraryCard(
                                item = item,
                                onItemClick = { clickedItem ->
                                    clickedItem.available = !clickedItem.available
                                    items = items.toMutableList().apply {
                                        val index = indexOfFirst { it.id == clickedItem.id }
                                        if (index != -1) {
                                            this[index] = clickedItem
                                        }
                                    }
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Элемент с id ${clickedItem.id}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
