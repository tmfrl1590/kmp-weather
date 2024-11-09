package com.kmp.weather.domain.repository

import com.kmp.weather.domain.dto.WeatherRemoteDto

interface WeatherRepository {

    suspend fun getWeatherInfo(lat: Double, lon: Double): WeatherRemoteDto
}