package com.adeho.coroutinesappa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.adeho.coroutinesappa.presentation.navegation.NavManager
import com.adeho.coroutinesappa.presentation.viewmodel.CoroutinesViewModel
import com.adeho.coroutinesappa.ui.theme.CoroutinesAppATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CoroutinesViewModel by viewModels()
            CoroutinesAppATheme {
                NavManager(viewModel)
            }
        }
    }
}

