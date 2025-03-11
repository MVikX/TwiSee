package com.example.twisee.auth

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.twisee.viewmodel.AuthViewModel

@Composable
fun WebViewLoginScreen(
    onTokenReceived: (String) -> Unit,
    viewModel: AuthViewModel
) {
    val twitchAuthUrl = viewModel.getTwitchAuthUrl()
    val context = LocalContext.current

    // open Twitch OAuth login on launch
    LaunchedEffect(Unit) {
        openCustomTab(context, twitchAuthUrl)
    }
}