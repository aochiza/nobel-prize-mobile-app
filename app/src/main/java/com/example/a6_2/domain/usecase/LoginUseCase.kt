package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String) {
        authRepository.login(username, password)
    }
}
