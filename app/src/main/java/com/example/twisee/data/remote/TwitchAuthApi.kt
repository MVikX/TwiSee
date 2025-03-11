package com.example.twisee.data.remote

import com.example.twisee.domain.model.GuestTokenResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface TwitchAuthApi {
    @POST("oauth2/token")
    suspend fun getGuestToken(
        @Query("client_id") clientId: String, // client ID
        @Query("client_secret") clientSecret: String, // secret key
        @Query("grant_type") grantType: String = "client_credentials" // authorization type
    ): GuestTokenResponse
}