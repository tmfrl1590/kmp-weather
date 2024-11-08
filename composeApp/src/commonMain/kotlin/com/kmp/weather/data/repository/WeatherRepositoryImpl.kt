package com.kmp.weather.data.repository

import com.kmp.weather.domain.WeatherRemoteDto
import com.kmp.weather.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath

class WeatherRepositoryImpl(
    private val httpClient: HttpClient
): WeatherRepository {
    override suspend fun getWeatherInfo(lat: Double, lon: Double): WeatherRemoteDto {

        val response = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.openweathermap.org"
                //path("/data/2.5/forecast")
                encodedPath = "/data/2.5/forecast"
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("appid", "6276022abfac9cf3f9be72f1d4cf6db9")
            }
        }

        return response.body()
    }
}