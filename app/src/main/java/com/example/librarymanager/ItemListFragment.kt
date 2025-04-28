
package com.example.librarymanager

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanager.ui.components.*

@Composable
fun ItemListFragment(
    viewModel: LibraryViewModel = viewModel(),
    isLandscape: Boolean = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
) {
    val uiState = viewModel.uiState.value
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.scrollPosition) {
        viewModel.scrollPosition?.let { (index, offset) ->
            listState.scrollToItem(index, offset)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.toggleTypeDialog(true) }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    LazyColumn {
                        items(5) {
                            ShimmerItem()
                        }
                    }
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadItems() }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        state = listState
                    ) {
                        items(uiState.items) { item ->
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
                }
            }
        }

        if (viewModel.showTypeDialog) {
            ItemTypeDialog(
                onDismiss = { viewModel.toggleTypeDialog(false) },
                onTypeSelected = { type ->
                    viewModel.updateSelectedType(type)
                    viewModel.toggleTypeDialog(false)
                }
            )
        }
    }
}
