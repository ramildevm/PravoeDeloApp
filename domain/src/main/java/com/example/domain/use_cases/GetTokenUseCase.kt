package com.example.domain.use_cases

import com.example.domain.repositories.AuthRepository

class GetTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(login: String, password:String) = repository.getToken(login = login, password = password)
}