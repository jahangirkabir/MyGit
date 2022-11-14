package com.jahanbabu.mygit.core.di

import com.jahanbabu.mygit.BuildConfig
import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.login.api.UserLoginService
import com.jahanbabu.mygit.features.userDetails.api.UserService
import com.jahanbabu.mygit.features.users.api.UsersListService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    companion object {

        const val API_URL = "apiUrl"
        const val MOSHI_ADAPTERS = "moshiAdapters"
        const val COMMON_HEADERS = "headers"

        @Provides
        fun provideBaseUrl() = BuildConfig.apiUrl

        @Singleton
        @Provides
        fun retrofit(
            baseUrl: String,
            client: OkHttpClient,
            moshi: Moshi,
        ): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        @Singleton
        @Provides
        fun moshi(@Named(MOSHI_ADAPTERS) adapters: Set<@JvmSuppressWildcards Any>): Moshi =
            Moshi.Builder().apply {
                for (adapter in adapters) {
                    add(adapter)
                }
                addLast(KotlinJsonAdapterFactory())
            }.build()

        @Singleton
        @Provides
        fun client(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
            OkHttpClient.Builder().apply {
                for (interceptor in interceptors) {
                    addInterceptor(interceptor)
                }
            }.build()

        @Singleton
        @Provides
        @IntoSet
        fun loggingInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )


        @Singleton
        @Provides
        fun api(apiManager: RetrofitApiManager): UserLoginService =
            apiManager.apiService(UserLoginService::class.java)

        @Singleton
        @Provides
        fun provideUsersListService(apiManager: RetrofitApiManager): UsersListService =
            apiManager.apiService(UsersListService::class.java)

        @Singleton
        @Provides
        fun provideUserDetailsService(apiManager: RetrofitApiManager): UserService =
            apiManager.apiService(UserService::class.java)
    }



    @Named(MOSHI_ADAPTERS)
    @Multibinds
    fun moshiAdapters(): Set<Any>

    @Multibinds
    fun interceptors(): Set<Interceptor>
}
