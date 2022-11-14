package com.jahanbabu.mygit.features.users.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val userId: Long,
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "twitter_username")
    val twitter: String?,
    @Json(name = "public_repos")
    val repos: Int?,
    @Json(name = "public_gists")
    val gists: Int?,
    val organisation: String?,
    val name: String? = "",
    val company: String? = "",
    val blog: String? = "",
    val email: String? = "",
    val bio: String? = "",
    val location: String? = "",
    val followers: Int? = 0,
    val following: Int? = 0
): Parcelable