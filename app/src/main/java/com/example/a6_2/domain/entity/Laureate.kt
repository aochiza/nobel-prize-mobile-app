package com.example.a6_2.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Laureate(
    val id: String,
    val fullName: String,
    val motivation: String,
    val birthDate: String?,
    val birthCountry: String?,
    val birthCity: String?,
    val portraitUrl: String?
)