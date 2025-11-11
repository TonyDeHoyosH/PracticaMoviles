package com.example.examen_practice.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examen_practice.Presentation.Views.FormularyView
import com.example.examen_practice.Presentation.Views.HomeView
import com.example.examen_practice.Presentation.Views.ModesView

@Composable
fun NavManager(darkModeStore: StoreDarkMode){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ){
        composable("Home"){
            HomeView(navController)
        }
        composable("Mode"){
            ModesView(navController, darkModeStore)
        }
        composable("Formulary"){
            FormularyView(navController)
        }
    }
}