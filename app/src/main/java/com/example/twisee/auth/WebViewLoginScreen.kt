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
    val twichAuthUrl = viewModel.getTwhichAuthUrl()

    //открытие URL в браузере
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twichAuthUrl))
    context.startActivity(intent)

    //получение URI, извлечение токена
    val redirectUri = getRedirectUri(context) //получение URI

    //проверка редиректа, извлечение токена
    if (redirectUri != null) {
        val token = extractToken(redirectUri)
        if (token != null) {
            viewModel.saveToken(token)
            onTokenReceived(token)
        }
    }
}

fun getRedirectUri(context: Context) : Uri? {
    //получение uri
    return (context as? Activity)?.intent?.data
}

fun extractToken(uri: Uri) : String? {
    return uri.fragment?.split("&")
        ?.find { it.startsWith("access_token=") }
        ?.substringAfter("=")
}