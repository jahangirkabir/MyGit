package com.jahanbabu.mygit.features.users.api


import com.jahanbabu.mygit.features.users.model.User
import retrofit2.Call
import retrofit2.http.*

interface UsersListService {

    @Headers("Accept: application/json")
    @GET("users")
    fun getUsers(
        @Header("authorization") token: String,
        @Query("since") pageIndex: Int,
        @Query("per_page") perPage: Int,
    ): Call<List<User>>
}
