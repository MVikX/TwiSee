package com.example.twisee.data.remote

import com.example.twisee.domain.model.GuestTokenResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface TwitchAuthApi {
    @POST("oauth2/token")
    suspend fun getGuestToken(
        @Query("client_id") clientId: String, // ID клиента
        @Query("client_secret") clientSecret: String, // секретный ключ
        @Query("grant_type") grantType: String = "client_credentials" // тип авторизации
    ): GuestTokenResponse
}
