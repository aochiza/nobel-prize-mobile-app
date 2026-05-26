package com.example.a6_2.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_2.data.AppDependencies
import com.example.a6_2.data.local.TokenManager
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.usecase.GetFavoritePrizesUseCase
import com.example.a6_2.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Success(val prizes: List<NobelPrize>) : FavoritesState()
    data class Error(val message: String) : FavoritesState()
}

class FavoritesViewModel : ViewModel() {

    private val getFavoritePrizesUseCase: GetFavoritePrizesUseCase by lazy {
        AppDependencies.getFavoritePrizesUseCase()
    }

    private val toggleFavoriteUseCase: ToggleFavoriteUseCase by lazy {
        AppDependencies.toggleFavoriteUseCase()
    }

    private val tokenManager: TokenManager by lazy {
        AppDependencies.tokenManager()
    }

    private val _state =
        MutableStateFlow<FavoritesState>(FavoritesState.Loading)

    val state: StateFlow<FavoritesState> =
        _state.asStateFlow()

    private val _favoriteIds =
        MutableStateFlow<Set<Int>>(emptySet())

    val favoriteIds: StateFlow<Set<Int>> =
        _favoriteIds.asStateFlow()

    private val _snackbarMessage =
        MutableStateFlow<String?>(null)

    val snackbarMessage: StateFlow<String?> =
        _snackbarMessage.asStateFlow()

    init {
        checkAuthAndLoadFavorites()
    }

    private fun checkAuthAndLoadFavorites() {
        viewModelScope.launch {

            val token = tokenManager.getToken()

            if (token.isNullOrBlank()) {
                _state.value =
                    FavoritesState.Error(
                        "Требуется авторизация"
                    )
                return@launch
            }

            loadFavorites()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {

            _state.value =
                FavoritesState.Loading

            try {
                val prizes =
                    getFavoritePrizesUseCase()

                _favoriteIds.value =
                    prizes.map { it.id }.toSet()

                _state.value =
                    if (prizes.isEmpty()) {
                        FavoritesState.Error(
                            "Нет избранных премий"
                        )
                    } else {
                        FavoritesState.Success(prizes)
                    }

            } catch (e: Exception) {

                _state.value =
                    FavoritesState.Error(
                        e.message
                            ?: "Не удалось загрузить избранное"
                    )
            }
        }
    }

    fun toggleFavorite(prizeId: Int) {
        if (prizeId == 0) return

        val isFavorite =
            prizeId in _favoriteIds.value

        val previousIds =
            _favoriteIds.value

        val previousState =
            _state.value

        _favoriteIds.update { ids ->
            if (isFavorite) ids - prizeId
            else ids + prizeId
        }

        if (
            previousState is FavoritesState.Success
            && isFavorite
        ) {
            val updated =
                previousState.prizes.filter {
                    it.id != prizeId
                }

            _state.value =
                if (updated.isEmpty()) {
                    FavoritesState.Error(
                        "Нет избранных премий"
                    )
                } else {
                    FavoritesState.Success(updated)
                }
        }

        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(
                    prizeId,
                    isFavorite
                )
            } catch (e: Exception) {

                _favoriteIds.value =
                    previousIds

                _state.value =
                    previousState

                _snackbarMessage.value =
                    e.message
                        ?: "Не удалось обновить избранное"
            }
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}
