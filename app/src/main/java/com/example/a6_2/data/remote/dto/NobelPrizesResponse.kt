package com.example.a6_2.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizesResponse(
    val nobelPrizes: List<NobelPrizeDto>,
    val meta: MetaDto? = null
)

@Serializable
data class MetaDto(
    val offset: Int,
    val limit: Int,
    val count: Int,
    val total: Int
)