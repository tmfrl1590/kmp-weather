package com.kmp.weather.domain.repository

import com.kmp.weather.domain.WeatherRemoteDto

interface WeatherRepository {

    suspend fun getWeatherInfo(lat: Double, lon: Double): WeatherRemoteDto
}