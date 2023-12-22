package com.example.pravoedeloapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.use_cases.GetCodeUseCase
import com.example.domain.use_cases.GetTokenUseCase
import com.example.domain.use_cases.RegenerateCodeUseCase

class LoginViewModelFactory(
    val getCodeUseCase: GetCodeUseCase,
    val getTokenUseCase: GetTokenUseCase,
    val regenerateCodeUseCase: RegenerateCodeUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            getCode = getCodeUseCase,
            regenerateCode = regenerateCodeUseCase,
            getToken = getTokenUseCase,
        ) as T
    }
}
