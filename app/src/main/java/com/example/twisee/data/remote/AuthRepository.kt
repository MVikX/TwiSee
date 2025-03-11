package com.example.twisee.data.remote

import com.example.twisee.ApiInfo.API_NUMBER
import com.example.twisee.ApiInfo.CLIENT_SECRET
import com.example.twisee.data.preferences.UserPreferences
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: TwitchAuthApi,
    private val userPreferences: UserPreferences
) {
    // get auth token from storage or fetch guest token
    suspend fun getAuthToken(): String {
        return userPreferences.getAuthToken()?.let { "Bearer $it" } ?: fetchGuestToken()
    }

    // fetch guest token if no user token is available
    suspend fun fetchGuestToken(): String {
        val response = authApi.getGuestToken(clientId = API_NUMBER, clientSecret = CLIENT_SECRET)
        return "Bearer ${response.accessToken}"
    }
}