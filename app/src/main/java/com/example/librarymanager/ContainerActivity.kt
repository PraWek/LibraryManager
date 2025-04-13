package com.example.librarymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanager.ui.theme.LibraryManagerTheme

class ContainerActivity : ComponentActivity() {
    private lateinit var viewModel: LibraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.selectedItem != null) {
                    viewModel.selectItem(null)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        setContent {
            LibraryManagerTheme {
                val viewModel: LibraryViewModel = viewModel()
                this.viewModel = viewModel
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLandscape) {
                        // Landscape layout - split screen
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.weight(1f)) {
                                ItemListFragment(viewModel)
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                DetailFragment(viewModel)
                            }
                        }
                    } else {
                        // Portrait layout - stack fragments
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (viewModel.selectedItem == null) {
                                ItemListFragment(viewModel)
                            } else {
                                DetailFragment(viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}