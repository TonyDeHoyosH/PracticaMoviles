package com.example.examen_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.example.examen_practice.Presentation.Navigation.StoreDarkMode
import com.example.examen_practice.Presentation.Navigation.NavManager
import com.example.examen_practice.ui.theme.Examen_practiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkModeStore = StoreDarkMode(this)
            val darkMode = darkModeStore.getDarkMode.collectAsState(false)
            Examen_practiceTheme(
                darkTheme = darkMode.value
            ) {
                NavManager(darkModeStore)
            }
        }
    }
}