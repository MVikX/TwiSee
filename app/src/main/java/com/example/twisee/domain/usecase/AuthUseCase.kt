package com.example.twisee.domain.usecase

import com.example.twisee.data.preferences.UserPreferences
import javax.inject.Inject
import com.example.twisee.ApiInfo.API_NUMBER
import com.example.twisee.ApiInfo.URL

class AuthUseCase @Inject constructor(
    private val userPreferences: UserPreferences
){
    //получение URL авторизации
    fun getTwichAutUrl(): String {
        return "https://id.twitch.tv/oauth2/authorize" +
                "?client_id=$API_NUMBER" +
                "&redirect_uri=$URL" +
                "&response_type=token" +
                "&scope=user:read:email user:read:follows"
    }

    //сохранение токена
    suspend fun saveAuthToken(token: String) {
        userPreferences.saveAuthToken(token)
    }
}