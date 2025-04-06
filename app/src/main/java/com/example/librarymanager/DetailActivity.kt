package com.example.librarymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.librarymanager.ui.theme.LibraryManagerTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemId = intent.getIntExtra("ITEM_ID", -1)
        val itemName = intent.getStringExtra("ITEM_NAME") ?: ""
        val itemType = intent.getStringExtra("ITEM_TYPE") ?: ""
        val detailedInfo = intent.getStringExtra("DETAILED_INFO") ?: ""

        setContent {
            LibraryManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                when (itemType) {
                                    "Книга" -> R.drawable.ic_book
                                    "Газета" -> R.drawable.ic_newspaper
                                    "Диск" -> R.drawable.ic_disc
                                    else -> R.drawable.ic_item
                                }
                            ),
                            contentDescription = "Item type icon",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = itemName,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Тип: $itemType",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ID: $itemId",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = detailedInfo,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
