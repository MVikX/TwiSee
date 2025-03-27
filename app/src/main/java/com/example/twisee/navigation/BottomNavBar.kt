package com.example.twisee.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun BottomNavBar(
    navController: NavController,
    avatarUrl: String?,
    currentRoute: String?,
    onRepeatHomeClick: () -> Unit,
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Profile,
    )

    NavigationBar(containerColor = Color(0xFF292929)) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        BottomNavItem.Home -> {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Главная",
                                tint = if (currentRoute == screen.route) Color(0xFF00C853)
                                else Color.Gray
                            )
                        }

                        BottomNavItem.Favorites -> {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Избранное",
                                tint = if (currentRoute == screen.route) Color(0xFF00C853)
                                else Color.Gray
                            )
                        }

                        BottomNavItem.Profile -> {
                            if (avatarUrl != null) {
                                Icon(
                                    painter = rememberAsyncImagePainter(avatarUrl),
                                    contentDescription = "Профиль",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Unspecified,
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Войти",
                                    tint = if (currentRoute == screen.route) Color(0xFF00C853)
                                    else Color.Gray
                                )
                            }
                        }
                    }
                },
                label = {
                    val labelText = if (screen == BottomNavItem.Profile) {
                        if (avatarUrl != null) "Профиль" else "Войти"
                    } else {
                        screen.title
                    }

                    Text(
                        text = labelText,
                        color = if (currentRoute == screen.route) Color(0xFF00C853)
                        else Color.White
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    when {
                        //double click in main
                        screen == BottomNavItem.Home && currentRoute == screen.route -> {
                            onRepeatHomeClick()
                        }

                        //double click in profile
                        screen == BottomNavItem.Profile && currentRoute?.startsWith(
                            "profile/") == true -> {
                            navController.navigate("profile_main") {
                                popUpTo("profile_main") { inclusive = false }
                                launchSingleTop = true
                            }
                        }

                        //default navigate
                        currentRoute != screen.route -> {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}