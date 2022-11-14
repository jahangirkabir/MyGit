package com.jahanbabu.mygit.features.login.di

import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.login.api.UserLoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginFragmentModule {

    companion object {

        @Singleton
        @Provides
        fun api(apiManager: RetrofitApiManager): UserLoginService =
            apiManager.apiService(UserLoginService::class.java)
    }
}