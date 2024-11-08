package com.kmp.weather.domain.dto

data class CityModel(
    val id: Int,
    val name: String,
    val country: String,
    val coord: CoordModel
)

data class CoordModel(
    val lon: Double,
    val lat: Double
)
