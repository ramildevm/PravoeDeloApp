package com.example.data.remote.api

import com.example.data.remote.model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
    @GET("getCode")
    suspend fun getCode(@Query("login") login: String): Response<UserInfoResponse>

    @GET("getToken")
    suspend fun getToken(@Query("login") login: String, @Query("password") password: String): Response<String>

    @GET("regenerateCode")
    suspend fun regenerateCode(@Query("login") login: String): Response<String>

    companion object {
        const val BASE_URL = "https://lk.pravoe-delo.su/api/v1/"
    }
}
