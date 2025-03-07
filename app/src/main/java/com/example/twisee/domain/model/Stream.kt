package com.example.twisee.domain.model

data class Stream(
    val id: String,
    val userName: String,
    val title: String,
    val viewerCount: Int,
    val thumbnailUrl: String
)