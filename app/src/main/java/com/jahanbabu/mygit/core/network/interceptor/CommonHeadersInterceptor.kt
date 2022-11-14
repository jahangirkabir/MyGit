package com.jahanbabu.mygit.core.network.interceptor

import com.jahanbabu.mygit.core.di.NetworkModule
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class CommonHeadersInterceptor @Inject constructor(
    @Named(NetworkModule.COMMON_HEADERS) private val headers: Provider<Map<String, String>>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val headers = request.headers.newBuilder().apply {
            for ((key, value) in headers.get()) {
                add(key, value)
            }
        }.build()

        return chain.proceed(request.newBuilder().headers(headers).build())
    }
}
