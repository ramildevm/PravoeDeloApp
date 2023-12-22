package com.example.pravoedeloapp.presentation.di

import com.example.domain.use_cases.GetCodeUseCase
import com.example.domain.use_cases.GetTokenUseCase
import com.example.domain.use_cases.RegenerateCodeUseCase
import com.example.pravoedeloapp.presentation.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideLoginViewModelFactory(
        getCodeUseCase: GetCodeUseCase,
        getTokenUseCase: GetTokenUseCase,
        regenerateCodeUseCase: RegenerateCodeUseCase,
    ): LoginViewModelFactory {
        return LoginViewModelFactory(
            getCodeUseCase = getCodeUseCase,
            regenerateCodeUseCase = regenerateCodeUseCase,
            getTokenUseCase = getTokenUseCase,
        )
    }
}