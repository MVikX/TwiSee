package com.example.twisee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String,
) {
    object Home : BottomNavItem("streams", Icons.Default.Home, "Главная")
    object Favorites: BottomNavItem("favorites", Icons.Default.Star, "Избранное")
    object Profile : BottomNavItem(
        "profile_main",
        Icons.Default.AccountCircle, "Профиль"
    )
}