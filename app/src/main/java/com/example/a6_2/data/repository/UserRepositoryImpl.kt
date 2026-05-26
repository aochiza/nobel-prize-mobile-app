package com.example.a6_2.data.repository

import com.example.a6_2.data.remote.ApiService
import com.example.a6_2.data.remote.mapper.toEntity
import com.example.a6_2.domain.entity.User
import com.example.a6_2.domain.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getCurrentUser(): User {
        return apiService.getCurrentUser().toEntity()
    }
}
