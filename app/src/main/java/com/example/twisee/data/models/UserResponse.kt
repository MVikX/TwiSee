package com.example.twisee.data.models

data class UserResponse(
    val data: List<UserInfo>
)

data class UserInfo(
    val id: String,
    val login: String,
    val display_name: String,
    val profile_image_url: String,
)