package com.example.a6_2.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrize(
    val awardYear: String,
    val category: String,
    val categoryFullName: String,
    val dateAwarded: String?,
    val laureates: List<Laureate>
)