package com.adeho.coroutinesappa.presentation.navegation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.adeho.coroutinesappa.presentation.viewmodel.CoroutinesViewModel
import com.adeho.coroutinesappa.presentation.views.ButtonsView
import com.adeho.coroutinesappa.presentation.views.DashboardView


@Composable
fun NavManager(viewModel: CoroutinesViewModel){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "DashboardView"
    ){
        composable(
            "DashboardView"
        ){
            DashboardView(navController)
        }

        composable(
            "ButtonsView"
        ){
            ButtonsView(navController, viewModel)
        }

    }
}

