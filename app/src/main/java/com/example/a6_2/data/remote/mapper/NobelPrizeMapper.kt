package com.example.a6_2.data.remote.mapper

import com.example.a6_2.data.remote.dto.LaureateDto
import com.example.a6_2.data.remote.dto.NobelPrizeDto
import com.example.a6_2.domain.entity.Laureate
import com.example.a6_2.domain.entity.NobelPrize


fun NobelPrizeDto.toEntity(): NobelPrize {
    return NobelPrize(
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