package com.example.a6_2.data.remote

import com.example.a6_2.data.remote.dto.LoginRequestDto
import com.example.a6_2.data.remote.dto.LoginResponseDto
import com.example.a6_2.data.remote.dto.PrizeDto
import com.example.a6_2.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ApiService {
    suspend fun login(username: String, password: String): LoginResponseDto
    suspend fun getPrizes(): List<PrizeDto>
    suspend fun getCurrentUser(): UserDto
    suspend fun getFavoritePrizes(): List<PrizeDto>
    suspend fun addFavorite(prizeId: Int)
    suspend fun removeFavorite(prizeId: Int)
}

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun login(username: String, password: String): LoginResponseDto {
        return client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username = username, password = password))
        }.body()
    }

    override suspend fun getPrizes(): List<PrizeDto> {
        return client.get("/prizes").body()
    }

    override suspend fun getCurrentUser(): UserDto {
        return client.get("/users/me").body()
    }

    override suspend fun getFavoritePrizes(): List<PrizeDto> {
        return client.get("/users/me/prizes").body()
    }

    override suspend fun addFavorite(prizeId: Int) {
        client.post("/users/me/prizes/$prizeId")
    }

    override suspend fun removeFavorite(prizeId: Int) {
        client.delete("/users/me/prizes/$prizeId")
    }
}
