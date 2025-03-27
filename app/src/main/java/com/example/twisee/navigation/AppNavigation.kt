package com.example.twisee.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.twisee.auth.WebViewLoginScreen
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

    // getting current screen route
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // trigger for scroll-to-top logic
    val refreshTrigger = remember { mutableStateOf(false)}



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
                // top bar
                TopAppBar(
                    title = {
                        Text(
                            "TwiSee",
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            },

            bottomBar = {
                // bottom navigation bar
                BottomNavBar(
                    navController = navController,
                    avatarUrl = userInfo?.profile_image_url,
                    currentRoute = currentRoute,
                    onRepeatHomeClick = {
                        refreshTrigger.value = true
                    },
                )
            }
        ) { paddingValues ->
            // screen navigation logic
            NavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
                startDestination = BottomNavItem.Home.route,
            ) {
                // main screen with top Twitch streams
                composable(BottomNavItem.Home.route) {
                    TopStreamsScreen(
                        streamViewModel = streamViewModel,
                        shouldScrollToTop = refreshTrigger.value,
                        onScrollHandled = { refreshTrigger.value = false }
                    )
                }

                // favorites (currently placeholder)
                composable(BottomNavItem.Favorites.route) {FavoritesScreen()}

                // profile screen
                composable("profile_main") {
                    HomeScreen(
                        username = userInfo?.display_name,
                        avatarUrl = userInfo?.profile_image_url,
                        navController = navController,
                        onLogout = {authViewModel.clearToken()},
                    )
                }

                // settings screen
                composable("profile/settings") {
                    SettingsScreen(themeViewModel)
                }

                // login
                composable("login") {
                    WebViewLoginScreen(
                        viewModel = authViewModel,
                        onTokenReceived = { token ->
                            authViewModel.saveToken(token)
                        }
                    )
                    navController.popBackStack("streams", inclusive = false)
                }
            }
        }
    }
}