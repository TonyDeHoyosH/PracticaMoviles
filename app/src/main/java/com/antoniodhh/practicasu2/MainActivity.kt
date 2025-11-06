package com.antoniodhh.practicasu2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.antoniodhh.practicasu2.presentation.NavManager.NavManager
import com.antoniodhh.practicasu2.ui.theme.PracticasU2Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasU2Theme(darkTheme = true) {
                NavManager()
        }
    }
}

}
