package com.jahanbabu.mygit.features.users.di

import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.users.api.UsersListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserListFragmentModule {

    companion object {
        @Singleton
        @Provides
        fun api(apiManager: RetrofitApiManager): UsersListService =
            apiManager.apiService(UsersListService::class.java)
    }
}