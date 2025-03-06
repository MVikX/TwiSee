package com.example.twisee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.twisee.navigation.AppNavigation
import com.example.twisee.ui.theme.TwiSeeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwiSeeTheme {
                AppNavigation()
            }
        }
    }
}