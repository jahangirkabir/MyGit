package com.jahanbabu.mygit.features.login.api


import com.jahanbabu.mygit.features.login.data.model.AccessToken
import retrofit2.Call
import retrofit2.http.*


interface UserLoginService {

    @Headers("Accept: application/json")
    @POST
    @FormUrlEncoded
    fun getAccessToken(
        @Url url: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Call<AccessToken>

    @Headers("Accept: application/json")
    @POST
    @FormUrlEncoded
    fun getRefreshToken(
        @Url url: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("refresh_token") code: String,
        @Field("grant_type") grantType: String = "refresh_token"
    ): Call<AccessToken>

}
