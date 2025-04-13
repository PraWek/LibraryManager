
package com.example.librarymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanager.ui.theme.LibraryManagerTheme

class ContainerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LibraryManagerTheme {
                val viewModel: LibraryViewModel = viewModel()
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

                // Handle orientation changes
                LaunchedEffect(isLandscape) {
                    viewModel.handleOrientationChange(isLandscape)
                }

                if (isLandscape) {
                    // Landscape layout - split screen
                    Row(modifier = Modifier.fillMaxSize()) {
                        // Left part - List fragment (always visible in landscape)
                        Box(modifier = Modifier.weight(1f)) {
                            ItemListFragment()
                        }

                        // Right part - Detail fragment (conditionally visible)
                        Box(modifier = Modifier.weight(1f)) {
                            if (viewModel.isDetailVisible) {
                                DetailFragment()
                            }
                        }
                    }
                } else {
                    // Portrait layout - single fragment at a time
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (viewModel.isDetailVisible) {
                            DetailFragment()
                        } else {
                            ItemListFragment()
                        }
                    }
                }

                // Handle back press
                BackHandler(enabled = viewModel.isDetailVisible) {
                    viewModel.onBackPressed()
                }
            }
        }
    }
}
