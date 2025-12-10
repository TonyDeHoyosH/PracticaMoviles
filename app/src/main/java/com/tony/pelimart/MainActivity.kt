package com.tony.pelimart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tony.pelimart.di.Injection
import com.tony.pelimart.ui.components.BottomNavBar
import com.tony.pelimart.ui.components.BottomNavItem
import com.tony.pelimart.ui.screens.FavoritesScreen
import com.tony.pelimart.ui.screens.HomeScreen
import com.tony.pelimart.ui.screens.ProvidersScreen
import com.tony.pelimart.ui.screens.SearchScreen
import com.tony.pelimart.ui.theme.PelimartTheme
import com.tony.pelimart.ui.viewmodel.FavoritesViewModel
import com.tony.pelimart.ui.viewmodel.HomeViewModel
import com.tony.pelimart.ui.viewmodel.ProvidersViewModel
import com.tony.pelimart.ui.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PelimartTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModelFactory = Injection.provideViewModelFactory(LocalContext.current)

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavBar(currentRoute = currentRoute) {
                navController.navigate(it) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { 
                val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory)
                HomeScreen(viewModel = homeViewModel) 
            }
            composable(BottomNavItem.Providers.route) { 
                val providersViewModel: ProvidersViewModel = viewModel(factory = viewModelFactory)
                ProvidersScreen(viewModel = providersViewModel)
            }
            composable(BottomNavItem.Search.route) { 
                val searchViewModel: SearchViewModel = viewModel(factory = viewModelFactory)
                SearchScreen(viewModel = searchViewModel)
            }
            composable(BottomNavItem.Favorites.route) { 
                val favoritesViewModel: FavoritesViewModel = viewModel(factory = viewModelFactory)
                FavoritesScreen(viewModel = favoritesViewModel)
            }
        }
    }
}
