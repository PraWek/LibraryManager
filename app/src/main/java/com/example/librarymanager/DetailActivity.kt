package com.example.librarymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.library.*
import com.example.librarymanager.ui.theme.LibraryManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isNewItem = intent.getBooleanExtra("is_new_item", false)
        val itemId = intent.getIntExtra("item_id", -1)
        val item = if (!isNewItem) LibraryManager().getAllItems().find { it.id == itemId } else null

        setContent {
            LibraryManagerTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Детали") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                ) { padding ->
                    if (item != null) {
                        ItemDetailContent(item, Modifier.padding(padding))
                    } else {
                        Text("Элемент не найден", Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetailContent(item: LibraryItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (item) {
            is Book -> {
                Text("Название: ${item.name}")
                Text("Автор: ${item.author}")
                Text("Количество страниц: ${item.pages}")
            }
            is Disc -> {
                Text("Название: ${item.name}")
                Text("Тип: ${item.type}")
            }
            else -> {
                Text("Название: ${item.name}")
            }
        }

        Text("ID: ${item.id}")
        Text("Доступность: ${if (item.available) "Да" else "Нет"}")
        Text("Подробная информация:")
        Text(item.getDetailedInfo())
    }
}