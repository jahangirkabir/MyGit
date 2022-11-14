package com.jahanbabu.mygit.features.userDetails.repository

import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.features.userDetails.datasource.UserDataSource
import com.jahanbabu.mygit.features.users.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailsRepository @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private val userDataSource: UserDataSource
) {

    suspend fun getUserDetails(username: String): ApiResponse<User> {
        val token = preferencesDataStore.getAccessToken().getOrDefault("")
        return userDataSource.getUserDetails(token, username)
    }
}

