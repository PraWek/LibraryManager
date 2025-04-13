package com.example.librarymanager

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanager.ui.components.AddItemDialog
import com.example.librarymanager.ui.components.ItemTypeDialog
import com.example.librarymanager.ui.components.LibraryCard


@Composable
fun ItemListFragment(
    viewModel: LibraryViewModel = viewModel()
) {
    val items = viewModel.items
    val showTypeDialog = viewModel.showTypeDialog
    val selectedType = viewModel.selectedType
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        viewModel.scrollPosition?.let { position ->
            listState.scrollToItem(position.index, position.offset)
        }
    }

    LaunchedEffect(key1 = listState.firstVisibleItemIndex, key2 = listState.firstVisibleItemScrollOffset) {
        viewModel.setScrollPosition(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.toggleTypeDialog(true) }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = listState
        ) {
            items(items) { item ->
                LibraryCard(
                    item = item,
                    onItemClick = { clickedItem ->
                        if (isLandscape) {
                            viewModel.showDetailInRight(clickedItem)
                        } else {
                            viewModel.navigateToDetail(clickedItem)
                        }
                    }
                )
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
                    viewModel.updateSelectedType(null)
                }
            )
        }
    }
}