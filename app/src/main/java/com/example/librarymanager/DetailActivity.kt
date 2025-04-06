
package com.example.librarymanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.librarymanager.ui.theme.LibraryManagerTheme

class DetailActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_ITEM_ID = "ITEM_ID"
        private const val EXTRA_ITEM_NAME = "ITEM_NAME"
        private const val EXTRA_ITEM_TYPE = "ITEM_TYPE"
        private const val EXTRA_DETAILED_INFO = "DETAILED_INFO"

        fun createIntent(context: Context, id: Int, name: String, type: String, detailedInfo: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ITEM_ID, id)
                putExtra(EXTRA_ITEM_NAME, name)
                putExtra(EXTRA_ITEM_TYPE, type)
                putExtra(EXTRA_DETAILED_INFO, detailedInfo)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemId = intent.getIntExtra(EXTRA_ITEM_ID, -1)
        val itemName = intent.getStringExtra(EXTRA_ITEM_NAME) ?: ""
        val itemType = intent.getStringExtra(EXTRA_ITEM_TYPE) ?: ""
        val detailedInfo = intent.getStringExtra(EXTRA_DETAILED_INFO) ?: ""

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
                        Text(
                            text = itemName,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = itemType,
                            style = MaterialTheme.typography.titleMedium
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
