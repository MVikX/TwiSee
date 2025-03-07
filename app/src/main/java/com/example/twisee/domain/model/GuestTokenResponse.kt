package com.example.twisee.domain.model

import com.google.gson.annotations.SerializedName

data class GuestTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int
)