package com.example.librarymanager

import android.content.res.Configuration
import kotlinx.coroutines.flow.snapshotFlow
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
import kotlinx.coroutines.flow.snapshotFlow


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

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.layoutInfo.visibleItemsInfo.size }
            .collect { (firstVisible, _) ->
                if (firstVisible <= 10 && !uiState.isLoading) {
                    viewModel.loadPreviousItems()
                }
            }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisible ->
                if (lastVisible != null &&
                    lastVisible >= uiState.items.size - 10 &&
                    !uiState.isLoading &&
                    uiState.hasMoreItems) {
                    viewModel.loadMoreItems()
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.items.isEmpty() && !uiState.isLoading) {
            Text(
                text = "No items found",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.items) { item ->
                    LibraryCard(
                        item = item,
                        onClick = { clickedItem ->
                            viewModel.selectItem(clickedItem)
                        }
                    )
                }

                if (uiState.isLoading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}