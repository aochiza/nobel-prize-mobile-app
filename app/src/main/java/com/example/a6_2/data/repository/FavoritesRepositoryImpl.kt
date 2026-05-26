package com.example.a6_2.data.repository

import com.example.a6_2.data.remote.ApiService
import com.example.a6_2.data.remote.mapper.toEntityListFromPrizes
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val apiService: ApiService
) : FavoritesRepository {

    override suspend fun getFavoritePrizes(): List<NobelPrize> {
        return apiService.getFavoritePrizes().toEntityListFromPrizes()
    }

    override suspend fun addFavorite(prizeId: Int) {
        apiService.addFavorite(prizeId)
    }

    override suspend fun removeFavorite(prizeId: Int) {
        apiService.removeFavorite(prizeId)
    }
}
