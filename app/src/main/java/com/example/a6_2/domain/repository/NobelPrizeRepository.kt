package com.example.a6_2.domain.repository

import com.example.a6_2.domain.entity.FilterParams
import com.example.a6_2.domain.entity.Laureate
import com.example.a6_2.domain.entity.NobelPrize

interface NobelPrizeRepository {
    suspend fun getPrizes(limit: Int = 50, offset: Int = 0): List<NobelPrize>
    suspend fun getPrizesFiltered(filterParams: FilterParams): List<NobelPrize>
    suspend fun getPrizeDetail(category: String, year: String): NobelPrize?
    suspend fun getLaureateDetail(laureateId: String): Laureate?
}