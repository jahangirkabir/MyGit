package com.jahanbabu.mygit.features.userDetails.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetails (
        val name: String,
        val email: String?,
        val twitter: String?,
        val organisation: String?,
        val bio: String?,
        val followers: Long,
        val following: Long,
        val avatarUrl: String?,
        val location: String?
)