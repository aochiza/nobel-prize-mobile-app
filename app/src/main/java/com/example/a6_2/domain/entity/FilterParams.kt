package com.example.a6_2.domain.entity

data class FilterParams(
    val year: Int? = null,
    val category: String? = null
) {
    fun isEmpty(): Boolean = year == null && category == null

    fun toQueryParams(): Map<String, String> {
        return buildMap {
            year?.let { put("nobelPrizeYear", it.toString()) }
            category?.let { put("nobelPrizeCategory", it) }
        }
    }
}