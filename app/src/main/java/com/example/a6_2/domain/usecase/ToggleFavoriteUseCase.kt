package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.repository.FavoritesRepository

class ToggleFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(prizeId: Int, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            favoritesRepository.removeFavorite(prizeId)
        } else {
            favoritesRepository.addFavorite(prizeId)
        }
    }
}
