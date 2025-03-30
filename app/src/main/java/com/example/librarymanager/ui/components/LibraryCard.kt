package com.example.librarymanager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.library.*
import com.example.librarymanager.R

@Composable
fun LibraryCard(
    item: LibraryItem,
    onItemClick: (LibraryItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClick(item)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (item.available) 10.dp else 1.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (item.available)
                MaterialTheme.colorScheme.surface.copy(alpha = 1f)
            else
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .alpha(if (item.available) 1f else 0.3f),
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
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = item.name)
                Text(text = "ID: ${item.id}")
            }
        }
    }
}
