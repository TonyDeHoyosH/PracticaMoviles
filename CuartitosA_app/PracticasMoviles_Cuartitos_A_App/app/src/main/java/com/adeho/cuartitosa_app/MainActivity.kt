package com.adeho.cuartitosa_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.adeho.cuartitosa_app.presentation.navegation.NavManager
import com.adeho.cuartitosa_app.ui.theme.CuartitosA_appTheme
import com.adeho.cuartitosa_app.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val studentViewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            CuartitosA_appTheme {
                NavManager(studentViewModel)
            }
        }
    }
}

