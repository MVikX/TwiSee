package com.example.twisee.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twisee.auth.WebViewLoginScreen
import com.example.twisee.viewmodel.AuthViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.twisee.viewmodel.StreamViewModel
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import com.example.twisee.ui.screens.TopStreamsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    streamViewModel: StreamViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold (
        topBar = {
            TopAppBar (
                title = {Text("TwiSee")},
                actions = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Войти")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ){ paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "streams",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("streams") {
                TopStreamsScreen(viewModel = streamViewModel)
            }
            composable("login") {
                WebViewLoginScreen(
                    onTokenReceived = {token ->
                        authViewModel.saveToken(token)
                        navController.popBackStack()
                    },
                    viewModel = authViewModel
                )
            }
        }
    }
}