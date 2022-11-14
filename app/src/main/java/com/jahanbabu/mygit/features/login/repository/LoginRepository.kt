package com.jahanbabu.mygit.features.login.repository

import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.features.login.data.model.AccessToken
import com.jahanbabu.mygit.features.login.datasource.LoginDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val preferencesDataStore: PreferencesDataStore
) {

    suspend fun isLoggedIn(): Pair<Boolean, String> {
        // check if access token is valid
        val token = preferencesDataStore.getAccessToken().getOrDefault("")
        if (token.isNotEmpty()){
            val refreshedToken = preferencesDataStore.getRefreshedToken().getOrDefault("")
            val tokenCreateTime = preferencesDataStore.getTokenCreateTime().getOrDefault(0)
            val currentTime = System.currentTimeMillis()
            val diff = currentTime - tokenCreateTime
            val diffHours = diff / (60 * 60 * 1000)
            if (diffHours > 7) {
                val newToken = processResponse(loginDataSource.getRefreshToken(refreshedToken))
                if (newToken == null || newToken.accessToken!!.isEmpty()){
                    return Pair(false, "Session expired, please login again.")
                } else{
                    preferencesDataStore.setAccessTokenCreateTime(System.currentTimeMillis())
                    preferencesDataStore.setAccessToken(newToken.accessToken)
                    newToken.refreshToken?.let { preferencesDataStore.setRefreshedToken(it) }
                }
                return Pair(true, "")
            }else return Pair(true, "")
        } else{
            return Pair(false, "")
        }
    }

    suspend fun getAccessToken(code: String, isNew: Boolean): ApiResponse<AccessToken> {
        return if (isNew){
            loginDataSource.getAccessToken(code)
        } else loginDataSource.getRefreshToken(code)
    }


    private fun processResponse(response: ApiResponse<AccessToken>): AccessToken? =
        when (response) {
            is ApiResponseSuccess.Entity -> response.entity
            is ApiResponseError -> {
                null
            }
        }
}
