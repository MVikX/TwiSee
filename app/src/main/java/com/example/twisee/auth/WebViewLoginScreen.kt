package com.example.twisee.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.twisee.viewmodel.AuthViewModel

@Composable
fun WebViewLoginScreen(
    onTokenReceived: (String) -> Unit,
    viewModel: AuthViewModel
) {
    val context = LocalContext.current
    val twitchAuthUrl = viewModel.getTwitchAuthUrl() // URL для авторизации

    // URL в браузере для входа
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitchAuthUrl))
    context.startActivity(intent)

    // редирект после успешного входа
    val redirectUri = getRedirectUri(context)

    // токен из URL
    if (redirectUri != null) {
        val token = extractToken(redirectUri)
        if (token != null) {
            viewModel.saveToken(token) // Сохраняем токен
            onTokenReceived(token) // Передаем токен в приложение
        }
    }
}

// URI редиректа (если браузер вернул ответ)
fun getRedirectUri(context: Context): Uri? {
    return (context as? Activity)?.intent?.data
}

// access_token из URL после авторизации
fun extractToken(uri: Uri): String? {
    return uri.fragment?.split("&")
        ?.find { it.startsWith("access_token=") }
        ?.substringAfter("=")
}
