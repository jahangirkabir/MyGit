package com.jahanbabu.mygit.features.login.datasource

import com.jahanbabu.mygit.BuildConfig
import com.jahanbabu.mygit.core.extension.Constants
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.login.api.UserLoginService
import com.jahanbabu.mygit.features.login.data.model.AccessToken
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val apiManager: RetrofitApiManager,
    private val service: UserLoginService
) {

    suspend fun getAccessToken(code: String): ApiResponse<AccessToken> {
        val request = service.getAccessToken(BuildConfig.domainUrl, BuildConfig.clientID, BuildConfig.clientSecret, code)
        return apiManager.call(request)
    }

    suspend fun getRefreshToken(refreshedToken: String): ApiResponse<AccessToken> {
        val request = service.getRefreshToken(BuildConfig.domainUrl, BuildConfig.clientID, BuildConfig.clientSecret, refreshedToken)
        return apiManager.call(request)
    }
}
