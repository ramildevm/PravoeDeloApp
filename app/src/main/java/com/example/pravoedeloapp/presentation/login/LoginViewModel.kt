package com.example.pravoedeloapp.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.Resource
import com.example.data.remote.model.UserInfo
import com.example.domain.use_cases.GetCodeUseCase
import com.example.domain.use_cases.GetTokenUseCase
import com.example.domain.use_cases.RegenerateCodeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getCode: GetCodeUseCase,
    private val regenerateCode: RegenerateCodeUseCase,
    private val getToken: GetTokenUseCase,
) : ViewModel() {
    var isCodeSent = MutableLiveData<Boolean>()
    val state: MutableLiveData<LoginState> = MutableLiveData<LoginState>()
    private val userInfo: MutableLiveData<UserInfo?> = MutableLiveData()
    private val authEventChannel = Channel<AuthEvent>()
    val authEvents = authEventChannel.receiveAsFlow()

    // if user is old make отправить код - не помните код? запросить новый
    init {
        state.value = LoginState()
        userInfo.value = UserInfo()
    }
    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.GetCode -> {
                isCodeSent.postValue(false)
                viewModelScope.launch{
                    when (val response = getCode(state.value?.phoneNumber ?: "")) {
                        is Resource.Error -> {
                            state.postValue(
                                state.value?.copy(
                                    phoneNumberError = response.message,
                                ),
                            )
                        }
                        is Resource.Loading -> return@launch
                        is Resource.Success -> {
                            println(response.data)
                            userInfo.postValue(response.data)
                        }
                    }
                }
            }
            LoginEvent.GetToken -> {
                println(userInfo.value)
                println(state.value)
                if (userInfo.value?.code != state.value?.code) {
                    state.postValue(
                        state.value?.copy(
                            codeError = "Неверный код",
                        ),
                    )
                    return
                }
                viewModelScope.launch {
                    when (val response = getToken(login = state.value?.phoneNumber ?: "", password = state.value?.code ?: "")) {
                        is Resource.Error -> {
                            state.postValue(
                                state.value?.copy(
                                    phoneNumberError = response.message,
                                ),
                            )
                        }
                        is Resource.Loading -> return@launch
                        is Resource.Success -> {
                            viewModelScope.launch {
                                response.data?.let {
                                    authEventChannel.send(AuthEvent.Success(it))
                                }
                            }
                        }
                    }
                }
            }
            LoginEvent.RegenerateCode -> {
                viewModelScope.launch{
                    when (val response = regenerateCode(state.value?.phoneNumber ?: "")) {
                        is Resource.Error -> {
                            state.postValue(
                                state.value?.copy(
                                    phoneNumberError = response.message,
                                ),
                            )
                        }
                        is Resource.Loading -> return@launch
                        is Resource.Success -> {
                            userInfo.postValue(
                                userInfo.value?.copy(
                                    code = response.data ?: "",
                                ),
                            )
                        }
                    }
                }
            }
            is LoginEvent.CodePanelStateChanged -> {
                isCodeSent.postValue(event.flag)
            }
            is LoginEvent.CodeChanged -> {
                state.postValue(
                    state.value?.copy(
                        code = event.code,
                        codeError = null,
                    ),
                )
            }
            is LoginEvent.PhoneNumberChanged -> {
                state.postValue(
                    state.value?.copy(
                        phoneNumber = event.number,
                        phoneNumberError = null,
                    ),
                )
            }
        }
    }
    sealed class AuthEvent {
        data class Success(val token: String) : AuthEvent()
        data object Error : AuthEvent()
    }
}
