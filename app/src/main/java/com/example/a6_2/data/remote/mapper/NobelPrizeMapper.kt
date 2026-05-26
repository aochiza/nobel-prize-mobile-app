package com.example.a6_2.data.remote.mapper

import com.example.a6_2.data.remote.dto.LaureateDto
import com.example.a6_2.data.remote.dto.NobelPrizeDto
import com.example.a6_2.data.remote.dto.PrizeDto
import com.example.a6_2.data.remote.dto.PrizeLaureateDto
import com.example.a6_2.domain.entity.Laureate
import com.example.a6_2.domain.entity.NobelPrize


fun NobelPrizeDto.toEntity(): NobelPrize {
    return NobelPrize(
        id = 0,
        awardYear = awardYear,
        category = category,
        categoryFullName = categoryFullName?.en
            ?: mapCategoryToRussian(category),
        dateAwarded = dateAwarded,
        laureates = laureates?.map { it.toEntity() } ?: emptyList()
    )
}

fun LaureateDto.toEntity(): Laureate {
    return Laureate(
        id = id,
        fullName = fullName?.en ?: "Unknown",
        motivation = motivation?.en ?: "No description available",
        birthDate = birth?.date,
        birthCountry = birth?.place?.country?.en,
        birthCity = birth?.place?.city?.en,
        portraitUrl = null  // API 2.1 не предоставляет фото
    )
}

fun List<NobelPrizeDto>.toEntityList(): List<NobelPrize> {
    return this.map { it.toEntity() }
}

fun PrizeDto.toEntity(): NobelPrize {
    val normalizedCategory = normalizeCategoryCode(category)
    return NobelPrize(
        id = id,
        awardYear = year,
        category = normalizedCategory,
        categoryFullName = mapCategoryToRussian(normalizedCategory),
        dateAwarded = null,
        laureates = laureates.map { it.toEntity(id) }
    )
}

fun PrizeLaureateDto.toEntity(prizeId: Int): Laureate {
    return Laureate(
        id = "${prizeId}_${name.hashCode()}",
        fullName = name,
        motivation = motivation.ifBlank { "No description available" },
        birthDate = null,
        birthCountry = null,
        birthCity = null,
        portraitUrl = null
    )
}

fun List<PrizeDto>.toEntityListFromPrizes(): List<NobelPrize> {
    return map { it.toEntity() }
}

fun normalizeCategoryCode(category: String): String {
    return when (category.lowercase()) {
        "physics", "phy" -> "phy"
        "chemistry", "che" -> "che"
        "medicine", "med", "physiology" -> "med"
        "literature", "lit" -> "lit"
        "peace", "pea" -> "pea"
        "economics", "economic", "eco" -> "eco"
        else -> category.lowercase()
    }
}

// Вспомогательная функция для маппинга категорий на русский
private fun mapCategoryToRussian(categoryCode: String): String {
    return when (categoryCode) {
        "phy" -> "Физика"
        "che" -> "Химия"
        "med" -> "Физиология и медицина"
        "lit" -> "Литература"
        "pea" -> "Мир"
        "eco" -> "Экономика"
        else -> categoryCode
    }
}