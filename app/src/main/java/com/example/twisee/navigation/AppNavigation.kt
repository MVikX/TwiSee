package com.example.twisee.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twisee.auth.WebViewLoginScreen
import com.example.twisee.ui.components.ProfileMenu
import com.example.twisee.ui.screens.*
import com.example.twisee.ui.theme.TwiSeeTheme
import com.example.twisee.viewmodel.AuthViewModel
import com.example.twisee.viewmodel.StreamViewModel
import com.example.twisee.ui.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    streamViewModel: StreamViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val token by authViewModel.authToken.collectAsState()
    val userInfo by authViewModel.userInfo.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    LaunchedEffect(token, userInfo) {
        if (token != null && userInfo != null) {
            streamViewModel.reloadStreams()
        } else if (token == null) {
            streamViewModel.fetchTopStreamsAsGuest()
        }
    }

    TwiSeeTheme(isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "TwiSee",
                            modifier = Modifier.clickable {
                                navController.popBackStack("streams", inclusive = false)
                            }
                        )
                    },
                    actions = {
                        if (token != null) {
                            ProfileMenu(
                                username = userInfo?.display_name ?: "Пользователь",
                                avatarUrl = userInfo?.profile_image_url,
                                onLogout = { authViewModel.clearToken() },
                                onProfileClick = { navController.navigate("profile") },
                                onSettingClick = { navController.navigate("settings") },
                                textColor = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            TextButton(
                                onClick = { navController.navigate("login") }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Войти",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(24.dp),
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "Войти",
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "streams",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("streams") {
                    TopStreamsScreen(streamViewModel = streamViewModel)
                }

                composable("profile") {
                    HomeScreen(
                        username = userInfo?.display_name,
                        avatarUrl = userInfo?.profile_image_url
                    )
                }

                composable("settings") {
                    SettingsScreen(themeViewModel)
                }

                composable("login") {
                    WebViewLoginScreen(
                        viewModel = authViewModel,
                        onTokenReceived = { token ->
                            authViewModel.saveToken(token)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}