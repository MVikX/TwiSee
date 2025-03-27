package com.example.twisee

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.example.twisee.navigation.AppNavigation
import com.example.twisee.ui.theme.DarkColors
import com.example.twisee.ui.theme.LightColors
import com.example.twisee.ui.theme.TwiSeeTheme
import com.example.twisee.ui.viewmodel.ThemeViewModel
import com.example.twisee.viewmodel.AuthViewModel
import com.example.twisee.viewmodel.StreamViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint



/**
 * TwiSee - a companion app for Twitch.
 * Author: MVikX
 * Created: 10.03.2025
 */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val streamViewModel: StreamViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val initialDarkTheme = themeViewModel.isDarkTheme.value
        setTheme(if (initialDarkTheme) R.style.Theme_TwiSee else R.style.Theme_TwiSee)


        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val systemUiController = rememberSystemUiController()
            val color = if (isDarkTheme) DarkColors.primary else LightColors.primary

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = color,
                    darkIcons = !isDarkTheme
                )
            }

            TwiSeeTheme(isDarkTheme = isDarkTheme) {
                AppNavigation(
                    authViewModel = authViewModel,
                    streamViewModel = streamViewModel,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        authViewModel.handleIntent(intent)
    }
}