package com.example.twisee.domain.usecase

import com.example.twisee.data.preferences.UserPreferences
import javax.inject.Inject
import com.example.twisee.ApiInfo.API_NUMBER
import com.example.twisee.ApiInfo.URL
import com.example.twisee.data.models.UserInfo
import com.example.twisee.data.remote.TwitchRepository

class AuthUseCase @Inject constructor(
    private val userPreferences: UserPreferences,
    private val repository: TwitchRepository
) {
    // generate Twitch OAuth URL
    fun getTwitchAuthUrl(): String {
        return "https://id.twitch.tv/oauth2/authorize" +
                "?client_id=$API_NUMBER" +
                "&redirect_uri=$URL" +
                "&response_type=token" +
                "&scope=user:read:email user:read:follows"
    }

    // save auth token to local storage
    suspend fun saveAuthToken(token: String) {
        userPreferences.saveAuthToken(token)
    }

    // get saved auth token
    suspend fun getAuthToken(): String? {
        return userPreferences.getAuthToken()
    }

    // clear auth token
    suspend fun clearAuthToken() {
        userPreferences.clearAuthToken()
    }

    // fetch user info using stored token
    suspend fun fetchUserInfo(): UserInfo? {
        return try {
            repository.getUserInfo()
        } catch (_: Exception) {
            null // return null if request fails
        }
    }
}