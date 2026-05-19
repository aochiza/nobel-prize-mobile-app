package com.example.a6_2.domain.usecase

import com.example.a6_2.domain.entity.Laureate
import com.example.a6_2.domain.repository.NobelPrizeRepository

class GetLaureateDetailUseCase(
    private val repository: NobelPrizeRepository
) {
    suspend operator fun invoke(laureateId: String): Laureate? {
        return try {
            repository.getLaureateDetail(laureateId)
        } catch (e: Exception) {
            null
        }
    }
}