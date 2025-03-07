package com.example.twisee.data.remote

import com.example.twisee.data.preferences.UserPreferences
import javax.inject.Inject
import com.example.twisee.ApiInfo.API_NUMBER
import com.example.twisee.ApiInfo.CLIENT_SECRET
import com.example.twisee.domain.model.Stream


class TwitchRepository @Inject constructor(
    private val api: TwitchApi,  // API для получения стримов
    private val authApi: TwitchAuthApi, // API для авторизации
    private val userPreferences: UserPreferences, // хранилище токена
) {
    private var paginationCursor: String? = null // пагинация

    // токен для запросов
    suspend fun getAuthToken(): String {
        val savedToken = userPreferences.getAuthToken()?.let { "Bearer $it" }

        // использование токена
        if (savedToken != null) return savedToken

        // запрос токена от Twitch(при отсуствии)
        val guestTokenResponse = authApi.getGuestToken(
            clientId = API_NUMBER,
            clientSecret = CLIENT_SECRET
        )

        val newToken = "Bearer ${guestTokenResponse.accessToken}"
        userPreferences.saveAuthToken(guestTokenResponse.accessToken) // новый токен
        return newToken
    }

    // список популярных стримов
    suspend fun fetchTopStreams(): List<Stream> {
        val token = getAuthToken() // актуальный токен
        val clientId = API_NUMBER

        val response = api.getTopStreams(
            auth = token,
            clientId = clientId,
            after = paginationCursor // передача курсора пагинации
        )
        paginationCursor = response.pagination?.cursor // сохранение курсора

        return response.data.map { it.toStream() } // конвертация DTO
    }


    suspend fun clearAuthToken() {
        userPreferences.clearAuthToken()
    }

    fun hasMorePages(): Boolean = paginationCursor != null
}
