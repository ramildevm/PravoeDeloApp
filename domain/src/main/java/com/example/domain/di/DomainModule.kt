package com.example.domain.di

import com.example.domain.repositories.AuthRepository
import com.example.domain.use_cases.GetCodeUseCase
import com.example.domain.use_cases.GetTokenUseCase
import com.example.domain.use_cases.RegenerateCodeUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetCodeUseCase(repository: AuthRepository): GetCodeUseCase {
        return GetCodeUseCase(repository)
    }

    @Provides
    fun provideGetTokenUseCase(repository: AuthRepository): GetTokenUseCase {
        return GetTokenUseCase(repository)
    }

    @Provides
    fun provideRegenerateCodeUseCase(repository: AuthRepository): RegenerateCodeUseCase {
        return RegenerateCodeUseCase(repository)
    }
}
