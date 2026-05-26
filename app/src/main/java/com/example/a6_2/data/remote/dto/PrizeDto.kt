package com.example.a6_2.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PrizeDto(
    val id: Int,
    val year: String,
    val category: String,
    val laureates: List<PrizeLaureateDto> = emptyList()
)

@Serializable
data class PrizeLaureateDto(
    val name: String,
    val motivation: String = ""
)
