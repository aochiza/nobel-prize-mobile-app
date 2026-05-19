package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.repository.NobelPrizeRepository

class GetPrizeDetailUseCase(
    private val repository: NobelPrizeRepository
) {
    suspend operator fun invoke(category: String, year: String): NobelPrize? {
        return repository.getPrizeDetail(category, year)
    }
}