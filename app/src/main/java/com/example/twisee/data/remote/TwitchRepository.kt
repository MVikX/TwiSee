package com.example.twisee.data.remote

import com.example.twisee.ApiInfo.API_NUMBER
import com.example.twisee.data.models.UserInfo
import com.example.twisee.domain.model.Stream
import javax.inject.Inject

class TwitchRepository @Inject constructor(
    private val api: TwitchApi,
    private val authRepository: AuthRepository
) {

    // fetch top streams from Twitch API
    suspend fun fetchTopStreams(after: String? = null): Pair<List<Stream>, String?> {
        val token = authRepository.getAuthToken()
        val response = api.getTopStreams(auth = token, clientId = API_NUMBER, after = after)
        return Pair(response.data.map { it.toStream() }, response.pagination?.cursor)
    }

    // fetch guest streams (no auth required)
    suspend fun fetchGuestStreams(): List<Stream> {
        val token = authRepository.fetchGuestToken()
        val response = api.getTopStreams(auth = token, clientId = API_NUMBER)
        return response.data.map { it.toStream() }
    }

    // get user info using stored token
    suspend fun getUserInfo(): UserInfo? {
        val token = authRepository.getAuthToken()
        val response = api.getUserInfo(auth = token, clientId = API_NUMBER)
        return response.data.firstOrNull() // get first user from list
    }
}