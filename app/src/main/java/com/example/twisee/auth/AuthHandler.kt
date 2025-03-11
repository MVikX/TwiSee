package com.example.twisee.auth

import android.content.Intent
import android.net.Uri

object AuthHandler {
    // extract token from OAuth redirect intent
    fun extractToken(intent: Intent?): String? {
        val data: Uri? = intent?.data
        return data?.getQueryParameter("access_token")
            ?: data?.encodedFragment?.split("&")
                ?.find { it.startsWith("access_token=") }
                ?.substringAfter("access_token=")
            ?: intent?.getStringExtra("access_token")
    }
}