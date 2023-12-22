package com.example.pravoedeloapp.presentation.login

data class LoginState(
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val code: String = "",
    val codeError: String? = null,
)
