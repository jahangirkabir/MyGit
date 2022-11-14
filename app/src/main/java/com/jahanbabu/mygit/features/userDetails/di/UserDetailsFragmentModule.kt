package com.jahanbabu.mygit.features.userDetails.di

import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.userDetails.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserDetailsFragmentModule {

    companion object {
        @Singleton
        @Provides
        fun api(apiManager: RetrofitApiManager): UserService =
            apiManager.apiService(UserService::class.java)
    }
}