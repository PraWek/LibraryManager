package com.example.librarymanager

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.library.Book
import com.example.library.Disc
import com.example.library.Newspaper

@Composable
fun DetailFragment(
    viewModel: LibraryViewModel = viewModel()
) {
    val item = viewModel.selectedItem

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (item == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Выберите элемент для просмотра деталей")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            when (item) {
                                is Book -> R.drawable.ic_book
                                is Newspaper -> R.drawable.ic_newspaper
                                is Disc -> R.drawable.ic_disc
                                else -> R.drawable.ic_item
                            }
                        ),
                        contentDescription = "Тип элемента",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.getDetailedInfo(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}