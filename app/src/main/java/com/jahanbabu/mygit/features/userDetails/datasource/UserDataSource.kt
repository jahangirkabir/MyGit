package com.jahanbabu.mygit.features.userDetails.datasource

import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.userDetails.api.UserService
import com.jahanbabu.mygit.features.users.model.User
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val apiManager: RetrofitApiManager,
    private val service: UserService
) {

    suspend fun getUserDetails(token: String, userName: String): ApiResponse<User> {
        val request = service.getUserDetails(token, userName)
        return apiManager.call(request)
    }
}
