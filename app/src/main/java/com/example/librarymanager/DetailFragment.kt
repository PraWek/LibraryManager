
package com.example.librarymanager

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailFragment(
    viewModel: LibraryViewModel = viewModel()
) {
    val selectedItem = viewModel.selectedItem

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (selectedItem != null) {
                Text(
                    text = selectedItem.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = selectedItem.getDetailedInfo(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
