package com.example.pravoedeloapp.presentation.login

sealed class LoginEvent {
    data object GetCode : LoginEvent()
    data object RegenerateCode : LoginEvent()
    data object GetToken : LoginEvent()
    data class PhoneNumberChanged(val number: String) : LoginEvent()
    data class CodeChanged(val code: String) : LoginEvent()
    data class CodePanelStateChanged(val flag: Boolean) : LoginEvent()
}
