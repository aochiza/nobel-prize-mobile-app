package com.example.a6_2.domain.repository

import com.example.a6_2.domain.entity.User

interface UserRepository {
    suspend fun getCurrentUser(): User
}
