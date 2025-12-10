package com.tony.pelimart.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector, val filledIcon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    object Providers : BottomNavItem("providers", "Providers", Icons.Outlined.Star, Icons.Filled.Star)
    object Search : BottomNavItem("search", "Search", Icons.Outlined.Search, Icons.Filled.Search)
    object Favorites : BottomNavItem("favorites", "Favorites", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)
}

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Providers,
        BottomNavItem.Search,
        BottomNavItem.Favorites
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) item.filledIcon else item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }
}
