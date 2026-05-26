package com.example.a6_2.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String)
    suspend fun logout()
}
