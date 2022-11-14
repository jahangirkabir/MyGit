package com.jahanbabu.mygit.features.userDetails.api


import com.jahanbabu.mygit.features.users.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @Headers("Accept: application/json")
    @GET("users/{user}")
    fun getUserDetails(
        @Header("authorization") token: String,
        @Path("user") username: String
    ): Call<User>
}
