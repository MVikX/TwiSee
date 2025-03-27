package com.example.twisee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen(
    username: String?,
    avatarUrl: String?,
    navController: NavController,
    onLogout: () -> Unit,
) {

    // check current system theme
    val isDark = isSystemInDarkTheme()

    // set button colors
    val buttonBackground = if (isDark) Color.White else Color.Black
    val buttonContent = if (isDark) Color.Black else Color.White

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // if user is logged in
        if (username != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // avatar
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl),
                    contentDescription = "Аватар",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                // login name
                Text(
                    text = username,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // settings button
                ElevatedButton(
                    onClick = { navController.navigate("profile/settings") },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = buttonBackground,
                        contentColor = buttonContent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Настройки",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Настройки",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }


                Spacer(modifier = Modifier.height(32.dp))

                // logout button
                ElevatedButton(
                    onClick = { onLogout() },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = buttonBackground,
                        contentColor = buttonContent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Выход",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Выход",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        } else {

            // if not logged in - login button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ElevatedButton(
                    onClick = {navController.navigate("login")},
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = buttonBackground,
                        contentColor = buttonContent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Вход",
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            "Войти через Twitch",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}