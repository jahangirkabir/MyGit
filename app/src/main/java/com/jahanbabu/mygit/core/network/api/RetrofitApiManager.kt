package com.jahanbabu.mygit.core.network.api

import android.util.Log
import com.jahanbabu.mygit.core.extension.Constants.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitApiManager @Inject internal constructor(
    private val retrofit: Retrofit
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun <T> call(call: Call<T>): ApiResponse<T> {
        return scope.async {
            val result = runCatching {
                call.execute()
            }
            return@async if (result.isSuccess) {
                processResponse(result.getOrThrow())
            } else {
                processError(result.exceptionOrNull())
            }
        }.await()
    }

    fun <T> apiService(service: Class<T>): T = retrofit.create(service)

    private fun <T> processResponse(response: Response<T>): ApiResponse<T> {
        val type = maintenanceType(response.errorBody()?.charStream()?.readText())

        return when (response.code()) {
            HttpURLConnection.HTTP_OK -> {
                response.body()?.let {
                    ApiResponseSuccess.Entity(it)
                } ?: ApiResponseError.Unexpected("Response body is null")
            }
            HttpURLConnection.HTTP_NO_CONTENT -> ApiResponseError.NoContent
            HttpURLConnection.HTTP_UNAUTHORIZED -> ApiResponseError.Unauthorized
            HttpURLConnection.HTTP_NOT_FOUND -> ApiResponseError.NotFound
            HTTP_UNPROCESSABLE_ENTITY -> ApiResponseError.InvalidRequest()
            HttpURLConnection.HTTP_UNAVAILABLE -> ApiResponseError.Maintenance(type)
            else -> {
                if (response.code() in 400..499) {
                    response.errorBody()
                    ApiResponseError.Message(statusCode = response.code())
                } else {
                    ApiResponseError.Unexpected(
                        msg = "Unexpected status code",
                        statusCode = response.code()
                    )
                }
            }
        }
    }

    private fun processError(error: Throwable?): ApiResponseError {
        return when {
            error is TimeoutException ||
                    error is SocketTimeoutException ||
                    error?.cause is TimeoutException ||
                    error?.cause is SocketTimeoutException -> ApiResponseError.Timeout
            error is UnknownHostException -> ApiResponseError.NoNetwork
            else -> ApiResponseError.Unexpected(error?.message ?: error?.cause?.message)
        }
    }

    private fun maintenanceType(type: String?): String {
        return try {
            JSONObject(type ?: "").getString(TYPE)
        } catch (ex: JSONException) {
            ""
        }
    }
}
