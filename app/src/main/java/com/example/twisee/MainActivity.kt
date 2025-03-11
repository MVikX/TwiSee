package com.example.twisee

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.twisee.navigation.AppNavigation
import com.example.twisee.viewmodel.AuthViewModel
import com.example.twisee.viewmodel.StreamViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * TwiSee - a companion app for Twitch.
 * Author: MVikX
 * Created: 10.03.2025
 */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val streamViewModel: StreamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(
                authViewModel = authViewModel,
                streamViewModel = streamViewModel
            )
        }
        authViewModel.handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        authViewModel.handleIntent(intent)
    }
}