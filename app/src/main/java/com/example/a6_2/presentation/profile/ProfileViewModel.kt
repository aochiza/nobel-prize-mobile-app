package com.example.a6_2.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_2.data.AppDependencies
import com.example.a6_2.domain.entity.User
import com.example.a6_2.domain.usecase.GetCurrentUserUseCase
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

class ProfileViewModel : ViewModel() {

    private val getCurrentUserUseCase: GetCurrentUserUseCase by lazy {
        AppDependencies.getCurrentUserUseCase()
    }

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val user = getCurrentUserUseCase()
                _state.value = ProfileState.Success(user)
            } catch (e: ClientRequestException) {
                _state.value = ProfileState.Error(
                    when (e.response.status.value) {
                        401 -> "Сессия истекла. Войдите снова"
                        else -> "Ошибка загрузки профиля (${e.response.status.value})"
                    }
                )
            } catch (e: ServerResponseException) {
                _state.value = ProfileState.Error("Ошибка сервера")
            } catch (e: Exception) {
                _state.value = ProfileState.Error(e.message ?: "Не удалось загрузить профиль")
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            AppDependencies.authRepository().logout()
            onLoggedOut()
        }
    }
}
