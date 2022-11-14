package com.jahanbabu.mygit.core.network.api

import java.net.HttpURLConnection

const val HTTP_UNPROCESSABLE_ENTITY = 422
const val HTTP_NO_NETWORK = -10
const val HTTP_UNEXPECTED = -11
const val MAINTENANCE = "Maintenance"
const val TYPE = "type"

sealed class ApiResponse<out T>(val statusCode: Int)

sealed class ApiResponseSuccess<T>(
    statusCode: Int
) : ApiResponse<T>(statusCode) {

    class Entity<T>(val entity: T) : ApiResponseSuccess<T>(HttpURLConnection.HTTP_OK)
}

sealed class ApiResponseError(val error: String? = null, statusCode: Int) :
    ApiResponse<Nothing>(statusCode) {

    object Timeout : ApiResponseError(statusCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT)

    object NoNetwork : ApiResponseError(statusCode = HTTP_NO_NETWORK)

    class Unexpected(val msg: String? = null, statusCode: Int = HTTP_UNEXPECTED) :
        ApiResponseError(msg, statusCode)

    class Message(
        val errorCode: String? = null,
        val msg: String? = null,
        statusCode: Int
    ) : ApiResponseError("${errorCode ?: "-1"}: ${msg ?: ""}", statusCode)

    object Unauthorized : ApiResponseError(statusCode = HttpURLConnection.HTTP_UNAUTHORIZED)

    object NotFound : ApiResponseError(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
    object NoContent : ApiResponseError(statusCode = HttpURLConnection.HTTP_NO_CONTENT)

    class InvalidRequest(val msg: String? = null) :
        ApiResponseError(msg, HTTP_UNPROCESSABLE_ENTITY)

    class Maintenance(msg: String? = null) :
        ApiResponseError(msg, HttpURLConnection.HTTP_UNAVAILABLE)
}

