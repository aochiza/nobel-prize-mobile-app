package com.example.a6_2.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizeDto(
    val awardYear: String,
    val category: String,
    val categoryFullName: FullNameDto?,
    val dateAwarded: String?,
    val laureates: List<LaureateDto>? = emptyList()
)