package com.example.twisee.data.remote

import com.example.twisee.data.models.UserResponse
import com.example.twisee.domain.model.Stream
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TwitchApi {
    @GET("streams")
    suspend fun getTopStreams(
        @Header("Authorization") auth: String, // authorization with token
        @Header("Client-Id") clientId: String, // client ID
        @Query("first") limit: Int = 10, // number of streams to fetch
        @Query("after") after: String? = null // pagination cursor
    ): TwitchResponse

    @GET("users")
    suspend fun getUserInfo(
        @Header("Authorization") auth: String,
        @Header("Client-Id") clientId: String,
    ): UserResponse
}

data class TwitchResponse(
    val data: List<StreamDto>,
    val pagination: Pagination?
)

data class StreamDto(
    val id: String,
    val user_name: String,
    val title: String,
    @SerializedName("viewer_count") val viewerCount: Int,
    val thumbnail_url: String
) {
    fun toStream(): Stream {
        return Stream(
            id = id,
            userName = user_name,
            title = title,
            viewerCount = viewerCount,
            thumbnailUrl = thumbnail_url.replace(
                "{width}", "320").replace("{height}", "180"
                )
        )
    }
}

// pagination cursor for next page
data class Pagination(val cursor: String?)