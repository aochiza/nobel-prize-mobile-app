package com.example.a6_2.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LaureateDto(
    val id: String,
    val fullName: FullNameDto?,
    val motivation: FullNameDto?,
    val birth: BirthDto?,
    val nobelPrizes: List<NobelPrizeRefDto>? = emptyList()
)

@Serializable
data class FullNameDto(
    val en: String?,
    val se: String?,
    val no: String?
)

@Serializable
data class BirthDto(
    val date: String?,
    val place: PlaceDto?
)

@Serializable
data class PlaceDto(
    val city: FullNameDto?,
    val country: FullNameDto?
)

@Serializable
data class NobelPrizeRefDto(
    val awardYear: String,
    val category: String
)