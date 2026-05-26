package com.example.a6_2.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_2.data.AppDependencies
import com.example.a6_2.domain.entity.FilterParams
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.usecase.FilterPrizesUseCase
import com.example.a6_2.domain.usecase.GetFavoritePrizesUseCase
import com.example.a6_2.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class PrizeListState {
    object Loading : PrizeListState()
    data class Success(val prizes: List<NobelPrize>) : PrizeListState()
    data class Error(val message: String) : PrizeListState()
}

class PrizeListViewModel : ViewModel() {

    private val filterPrizesUseCase: FilterPrizesUseCase by lazy {
        AppDependencies.filterPrizesUseCase()
    }

    private val getFavoritePrizesUseCase: GetFavoritePrizesUseCase by lazy {
        AppDependencies.getFavoritePrizesUseCase()
    }

    private val toggleFavoriteUseCase: ToggleFavoriteUseCase by lazy {
        AppDependencies.toggleFavoriteUseCase()
    }

    private val _state = MutableStateFlow<PrizeListState>(PrizeListState.Loading)
    val state: StateFlow<PrizeListState> = _state.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    private val _filterParams = MutableStateFlow(FilterParams())
    val filterParams: StateFlow<FilterParams> = _filterParams.asStateFlow()

    private val availableYears = (1901..2024).toList().sortedDescending()
    private val categories = listOf(
        "phy" to "Физика",
        "che" to "Химия",
        "med" to "Медицина",
        "lit" to "Литература",
        "pea" to "Мир",
        "eco" to "Экономика"
    )

    init {
        loadPrizes()
        loadFavoriteIds()
    }

    fun loadFavoriteIds() {
        viewModelScope.launch {
            try {
                val favorites = getFavoritePrizesUseCase()
                _favoriteIds.value = favorites.map { it.id }.toSet()
            } catch (_: Exception) {
                // Избранное недоступно без авторизации — не блокируем список
            }
        }
    }

    fun toggleFavorite(prizeId: Int) {
        if (prizeId == 0) return

        val isFavorite = prizeId in _favoriteIds.value
        val previousIds = _favoriteIds.value

        _favoriteIds.update { ids ->
            if (isFavorite) ids - prizeId else ids + prizeId
        }

        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(prizeId, isFavorite)
            } catch (e: Exception) {
                _favoriteIds.value = previousIds
                _snackbarMessage.value = e.message ?: "Не удалось обновить избранное"
            }
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    fun loadPrizes() {
        viewModelScope.launch {
            _state.value = PrizeListState.Loading
            try {
                val prizes = filterPrizesUseCase(_filterParams.value)
                if (prizes.isEmpty()) {
                    _state.value = PrizeListState.Error("Нет данных. Попробуйте изменить фильтры")
                } else {
                    _state.value = PrizeListState.Success(prizes)
                }
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("403") == true -> "Доступ запрещён (403). Проверьте интернет"
                    e.message?.contains("timeout") == true -> "Превышено время ожидания"
                    e.message?.contains("UnknownHost") == true -> "Нет подключения к интернету"
                    else -> e.message ?: "Неизвестная ошибка"
                }
                _state.value = PrizeListState.Error(errorMessage)
            }
        }
    }

    fun updateYear(year: Int?) {
        _filterParams.update { it.copy(year = year) }
        loadPrizes()
    }

    fun updateCategory(category: String?) {
        _filterParams.update { it.copy(category = category) }
        loadPrizes()
    }

    fun clearFilters() {
        _filterParams.update { FilterParams() }
        loadPrizes()
    }

    fun getAvailableYears(): List<Int> = availableYears
    fun getCategories(): List<Pair<String, String>> = categories
}