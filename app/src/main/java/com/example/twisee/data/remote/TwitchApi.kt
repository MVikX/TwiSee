package com.example.twisee.data.remote

import com.example.twisee.domain.model.Stream
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TwitchApi {
    @GET("streams")
    suspend fun getTopStreams(
        @Header("Authorization") auth: String, // авторизация с токеном
        @Header("Client-id") clientId: String, // клиентский ID
        @Query("first") limit: Int = 10, // количество стримов
        @Query("after") after: String? = null // пагинация
    ): TwitchResponse
}

data class TwitchResponse(
    val data: List<StreamDto>,
    val pagination: Pagination?
)

data class StreamDto(
    val id: String,
    val user_name: String,
    val title: String,
    val view_count: Int,
    val thumbnail_url: String
) {
    fun toStream(): Stream {
        return Stream(
            id = id,
            userName = user_name,
            title = title,
            viewerCount = view_count,
            thumbnailUrl = thumbnail_url.replace(
                "{width}",
                "320").replace(
                "{height}",
                "180"
                )
        )
    }
}

// курсор для следующей страницы
data class Pagination(val cursor: String?)
