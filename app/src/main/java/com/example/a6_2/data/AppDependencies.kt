package com.example.a6_2.data

import android.content.Context
import com.example.a6_2.data.local.TokenManager
import com.example.a6_2.data.remote.ApiService
import com.example.a6_2.data.remote.ApiServiceImpl
import com.example.a6_2.data.remote.KtorClientFactory
import com.example.a6_2.data.repository.AuthRepositoryImpl
import com.example.a6_2.data.repository.NobelPrizeRepositoryImpl
import com.example.a6_2.domain.repository.AuthRepository
import com.example.a6_2.domain.repository.NobelPrizeRepository
import com.example.a6_2.domain.repository.FavoritesRepository
import com.example.a6_2.domain.repository.UserRepository
import com.example.a6_2.domain.usecase.FilterPrizesUseCase
import com.example.a6_2.domain.usecase.GetCurrentUserUseCase
import com.example.a6_2.domain.usecase.GetFavoritePrizesUseCase
import com.example.a6_2.domain.usecase.LoginUseCase
import com.example.a6_2.domain.usecase.ToggleFavoriteUseCase
import com.example.a6_2.data.repository.FavoritesRepositoryImpl
import com.example.a6_2.data.repository.UserRepositoryImpl
import io.ktor.client.HttpClient

object AppDependencies {

    private lateinit var appContext: Context

    val tokenManager: TokenManager by lazy {
        TokenManager(appContext)
    }

    fun tokenManager(): TokenManager {
        return tokenManager
    }
    private val httpClient: HttpClient by lazy {
        KtorClientFactory.createClient(tokenManager)
    }

    val apiService: ApiService by lazy {
        ApiServiceImpl(httpClient)
    }

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun nobelPrizeRepository(): NobelPrizeRepository =
        NobelPrizeRepositoryImpl(apiService)

    fun filterPrizesUseCase(): FilterPrizesUseCase =
        FilterPrizesUseCase(nobelPrizeRepository())

    fun authRepository(): AuthRepository =
        AuthRepositoryImpl(apiService, tokenManager)

    fun loginUseCase(): LoginUseCase =
        LoginUseCase(authRepository())

    fun userRepository(): UserRepository =
        UserRepositoryImpl(apiService)

    fun favoritesRepository(): FavoritesRepository =
        FavoritesRepositoryImpl(apiService)

    fun getCurrentUserUseCase(): GetCurrentUserUseCase =
        GetCurrentUserUseCase(userRepository())

    fun getFavoritePrizesUseCase(): GetFavoritePrizesUseCase =
        GetFavoritePrizesUseCase(favoritesRepository())

    fun toggleFavoriteUseCase(): ToggleFavoriteUseCase =
        ToggleFavoriteUseCase(favoritesRepository())
}
