package com.example.a6_2.domain.repository

import com.example.a6_2.domain.entity.NobelPrize

interface FavoritesRepository {
    suspend fun getFavoritePrizes(): List<NobelPrize>
    suspend fun addFavorite(prizeId: Int)
    suspend fun removeFavorite(prizeId: Int)
}
