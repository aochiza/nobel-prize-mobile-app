package com.example.a6_2.data.remote

import com.example.a6_2.data.AuthSession
import com.example.a6_2.data.local.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object KtorClientFactory {
    private const val BASE_URL = "http://10.0.2.2:8080"

    fun createClient(
        tokenManager: TokenManager
    ): HttpClient {

        return HttpClient(Android) {

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor: $message")
                    }
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }

            install(HttpCallValidator) {
                validateResponse { response ->

                    if (
                        response.status == HttpStatusCode.Unauthorized
                    ) {
                        runBlocking {
                            tokenManager.clearToken()
                        }

                        AuthSession.notifySessionExpired()
                    }
                }
            }

            defaultRequest {
                url(BASE_URL)

                header(
                    HttpHeaders.Accept,
                    "application/json"
                )

                val token = runBlocking {
                    tokenManager.getToken()
                }

                if (!token.isNullOrBlank()) {
                    header(
                        HttpHeaders.Authorization,
                        "Bearer $token"
                    )
                }
            }
        }
    }
}