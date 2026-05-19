package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.entity.FilterParams
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.repository.NobelPrizeRepository

class FilterPrizesUseCase(
    private val repository: NobelPrizeRepository
) {
    suspend operator fun invoke(filterParams: FilterParams): List<NobelPrize> {
        return if (filterParams.isEmpty()) {
            repository.getPrizes(limit = 100, offset = 0)
        } else {
            repository.getPrizesFiltered(filterParams)
        }
    }
}