package com.example.a6_2.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import com.example.a6_2.data.remote.dto.NobelPrizesResponse

interface ApiService {
    suspend fun getNobelPrizes(
        limit: Int = 50,
        offset: Int = 0,
        year: Int? = null,
        category: String? = null
    ): NobelPrizesResponse
}

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun getNobelPrizes(
        limit: Int,
        offset: Int,
        year: Int?,
        category: String?
    ): NobelPrizesResponse {
        return client.get("/nobelPrizes") {  //путь от базового URL
            parameter("limit", limit)
            parameter("offset", offset)
            year?.let { parameter("nobelPrizeYear", it) }
            category?.let { parameter("nobelPrizeCategory", it) }
        }.body()
    }
}