package com.example.data

import com.example.common.util.Resource
import com.example.data.remote.api.AuthApi
import com.example.data.remote.mappers.toModel
import com.example.data.remote.model.UserInfo
import com.example.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun getCode(login: String): Resource<UserInfo> {
        val response = api.getCode(login = login)
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toModel())
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun regenerateCode(login: String): Resource<String> {
        val response = api.regenerateCode(login = login)
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getToken(login: String, password: String): Resource<String> {
        val response = api.getToken(login = login, password = password)
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}