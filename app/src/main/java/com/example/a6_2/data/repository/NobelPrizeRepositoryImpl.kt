package com.example.a6_2.data.repository

import com.example.a6_2.data.remote.ApiService
import com.example.a6_2.data.remote.MockData
import com.example.a6_2.data.remote.mapper.toEntity
import com.example.a6_2.data.remote.mapper.toEntityList
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
            val response = apiService.getNobelPrizes(limit, offset)
            response.nobelPrizes.toEntityList()
        }
    }

    override suspend fun getPrizesFiltered(filterParams: FilterParams): List<NobelPrize> {
        return if (useMockData) {
            var prizes = MockData.getMockNobelPrizesResponse().nobelPrizes.toEntityList()
            filterParams.year?.let { year ->
                prizes = prizes.filter { it.awardYear == year.toString() }
            }
            filterParams.category?.let { category ->
                prizes = prizes.filter { it.category == category }
            }
            prizes
        } else {
            val response = apiService.getNobelPrizes(
                limit = 100,
                offset = 0,
                year = filterParams.year,
                category = filterParams.category
            )
            response.nobelPrizes.toEntityList()
        }
    }

    override suspend fun getPrizeDetail(category: String, year: String): NobelPrize? {
        return if (useMockData) {
            getPrizesFiltered(FilterParams(year = year.toIntOrNull(), category = category)).firstOrNull()
        } else {
            val response = apiService.getNobelPrizes(
                limit = 1,
                offset = 0,
                year = year.toIntOrNull(),
                category = category
            )
            response.nobelPrizes.firstOrNull()?.toEntity()
        }
    }

    override suspend fun getLaureateDetail(laureateId: String): Laureate? {
        return if (useMockData) {
            getPrizes(100, 0).flatMap { it.laureates }.find { it.id == laureateId }
        } else {
            null
        }
    }
}