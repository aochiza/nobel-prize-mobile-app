package com.example.a6_2.data.repository

import com.example.a6_2.data.remote.ApiService
import com.example.a6_2.data.remote.MockData
import com.example.a6_2.data.remote.mapper.toEntity
import com.example.a6_2.data.remote.mapper.toEntityList
import com.example.a6_2.data.remote.mapper.toEntityListFromPrizes
import com.example.a6_2.domain.entity.FilterParams
import com.example.a6_2.domain.entity.Laureate
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.domain.repository.NobelPrizeRepository

class NobelPrizeRepositoryImpl(
    private val apiService: ApiService
) : NobelPrizeRepository {

    private val useMockData = false

    override suspend fun getPrizes(limit: Int, offset: Int): List<NobelPrize> {
        return if (useMockData) {
            MockData.getMockNobelPrizesResponse().nobelPrizes.toEntityList()
        } else {
            apiService.getPrizes().toEntityListFromPrizes()
        }
    }

    override suspend fun getPrizesFiltered(filterParams: FilterParams): List<NobelPrize> {
        return applyFilters(getPrizes(limit = 100, offset = 0), filterParams)
    }

    override suspend fun getPrizeDetail(category: String, year: String): NobelPrize? {
        return if (useMockData) {
            getPrizesFiltered(FilterParams(year = year.toIntOrNull(), category = category)).firstOrNull()
        } else {
            getPrizesFiltered(FilterParams(year = year.toIntOrNull(), category = category)).firstOrNull()
        }
    }

    override suspend fun getLaureateDetail(laureateId: String): Laureate? {
        return if (useMockData) {
            getPrizes(100, 0).flatMap { it.laureates }.find { it.id == laureateId }
        } else {
            getPrizes(100, 0).flatMap { it.laureates }.find { it.id == laureateId }
        }
    }

    private fun applyFilters(prizes: List<NobelPrize>, filterParams: FilterParams): List<NobelPrize> {
        var result = prizes
        filterParams.year?.let { year ->
            result = result.filter { it.awardYear == year.toString() }
        }
        filterParams.category?.let { category ->
            result = result.filter { it.category == category }
        }
        return result
    }
}
