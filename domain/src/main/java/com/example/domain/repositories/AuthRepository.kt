package com.example.domain.repositories

import com.example.common.util.Resource
import com.example.data.remote.model.UserInfo

interface AuthRepository {
    suspend fun getCode(login: String): Resource<UserInfo>
    suspend fun regenerateCode(login: String): Resource<String>
    suspend fun getToken(login: String, password: String): Resource<String>
}
