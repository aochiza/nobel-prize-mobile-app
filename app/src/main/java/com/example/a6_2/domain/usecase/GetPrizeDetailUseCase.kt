package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.repository.NobelPrizeRepository

class GetPrizesUseCase(
    private val repository: NobelPrizeRepository
) {
    suspend operator fun invoke(limit: Int = 50, offset: Int = 0): List<NobelPrize> {
        return repository.getPrizes(limit, offset)
    }
}