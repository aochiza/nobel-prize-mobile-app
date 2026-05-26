package com.example.a6_2.data.repository

import com.example.a6_2.data.local.TokenManager
import com.example.a6_2.data.remote.ApiService
import com.example.a6_2.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(username: String, password: String) {
        val response = apiService.login(username, password)
        tokenManager.saveToken(response.token)
    }

    override suspend fun logout() {
        tokenManager.clearToken()
    }
}
