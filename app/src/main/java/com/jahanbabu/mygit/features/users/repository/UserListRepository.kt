package com.jahanbabu.mygit.features.users.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.users.api.UsersListService
import com.jahanbabu.mygit.features.users.datasource.UsersPagingSource
import com.jahanbabu.mygit.features.users.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListRepository @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private val apiManager: RetrofitApiManager,
    private val service: UsersListService
) {

    fun getUserList(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { UsersPagingSource(preferencesDataStore, apiManager, service) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}

