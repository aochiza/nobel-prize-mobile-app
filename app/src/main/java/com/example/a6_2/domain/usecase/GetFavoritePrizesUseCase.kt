package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.repository.FavoritesRepository

class GetFavoritePrizesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(): List<NobelPrize> = favoritesRepository.getFavoritePrizes()
}
