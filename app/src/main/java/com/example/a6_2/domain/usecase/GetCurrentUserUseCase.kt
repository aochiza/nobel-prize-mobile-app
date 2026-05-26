package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.entity.User
import com.example.a6_2.domain.repository.UserRepository

class GetCurrentUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User = userRepository.getCurrentUser()
}
