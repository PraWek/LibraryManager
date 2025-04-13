
package com.example.librarymanager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanager.ui.components.LibraryCard

@Composable
fun ItemListFragment(
    viewModel: LibraryViewModel = viewModel()
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.listScrollPosition
    )

    // Save scroll position when leaving the list
    DisposableEffect(Unit) {
        onDispose {
            viewModel.listScrollPosition = listState.firstVisibleItemIndex
        }
    }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddItemClick() }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(viewModel.items) { item ->
                LibraryCard(
                    item = item,
                    onItemClick = {
                        if (isLandscape) {
                            viewModel.showDetailInRight(item)
                        } else {
                            viewModel.navigateToDetail(item)
                        }
                    }
                )
            }
        }
    }
}
