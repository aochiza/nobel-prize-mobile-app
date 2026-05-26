package com.example.a6_2.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrize(
    val id: Int = 0,
    val awardYear: String,
    val category: String,
    val categoryFullName: String,
    val dateAwarded: String?,
    val laureates: List<Laureate>
)