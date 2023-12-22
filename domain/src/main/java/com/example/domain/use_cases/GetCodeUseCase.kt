package com.example.domain.use_cases

import com.example.domain.repositories.AuthRepository

class GetCodeUseCase (private val repository: AuthRepository) {
    suspend operator fun invoke(login: String) = repository.getCode(login = login)
}