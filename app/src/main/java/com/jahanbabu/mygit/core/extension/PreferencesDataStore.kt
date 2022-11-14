package com.jahanbabu.mygit.core.extension

interface PreferencesDataStore {

    suspend fun setAccessToken(
        accessToken: String
    )

    suspend fun getAccessToken(): Result<String>

    suspend fun setRefreshedToken(
        refreshedToken: String
    )

    suspend fun getRefreshedToken(): Result<String>

    suspend fun setAccessTokenCreateTime(
        tokenCreateTime: Long
    )

    suspend fun getTokenCreateTime(): Result<Long>
}