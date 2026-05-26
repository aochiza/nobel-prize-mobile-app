package com.example.a6_2.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_2.data.AppDependencies
import com.example.a6_2.domain.usecase.LoginUseCase
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val loginUseCase: LoginUseCase by lazy {
        AppDependencies.loginUseCase()
    }

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _state.value = LoginState.Error("Введите логин и пароль")
            return
        }

        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                loginUseCase(username.trim(), password)
                _state.value = LoginState.Success
            } catch (e: ClientRequestException) {
                _state.value = LoginState.Error(
                    when (e.response.status.value) {
                        401, 403 -> "Неверный логин или пароль"
                        else -> "Ошибка входа (${e.response.status.value})"
                    }
                )
            } catch (e: ServerResponseException) {
                _state.value = LoginState.Error("Ошибка сервера. Попробуйте позже")
            } catch (e: Exception) {
                val message = when {
                    e.message?.contains("timeout", ignoreCase = true) == true ->
                        "Превышено время ожидания"
                    e.message?.contains("UnknownHost", ignoreCase = true) == true ->
                        "Нет подключения к серверу"
                    else -> e.message ?: "Не удалось войти"
                }
                _state.value = LoginState.Error(message)
            }
        }
    }

    fun resetState() {
        if (_state.value is LoginState.Error) {
            _state.value = LoginState.Idle
        }
    }
}
