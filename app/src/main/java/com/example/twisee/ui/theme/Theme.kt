package com.example.twisee.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light theme color scheme
val LightColors = lightColorScheme(
    primary = Color(0xFFBB86FC), // Light purple
    background = Color.White,
    surface = Color(0xFFF5F5F5), // Light gray for cards
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

// Dark theme color scheme
val DarkColors = darkColorScheme(
    primary = Color(0xFF7B1FA2), // Dark purple
    background = Color(0xFF121212), // Almost black
    surface = Color(0xFF1E1E1E), // Dark gray for cards
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.LightGray
)

@Composable
fun TwiSeeTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}