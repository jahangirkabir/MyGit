package com.jahanbabu.mygit.features.login.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessToken(
    @Json(name = "access_token")
    val accessToken: String? = "",
    @Json(name = "expires_in")
    val expiresIn: String? = "",
    @Json(name = "refresh_token")
    val refreshToken: String? = "",
    @Json(name = "refresh_token_expires_in")
    val refreshTokenExpiresIn: String? = "",
    @Json(name = "error")
    val error: String? = "",
    @Json(name = "error_description")
    val errorDescription: String? = ""
)