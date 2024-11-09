package com.kmp.weather.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityModel(
    val id: Int,
    val name: String,
    val country: String,
    val coord: CoordModel
)

@Serializable
data class CoordModel(
    val lon: Double,
    val lat: Double
)
