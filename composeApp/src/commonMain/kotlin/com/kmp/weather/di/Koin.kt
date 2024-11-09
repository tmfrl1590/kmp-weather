package com.kmp.weather.di

import com.kmp.weather.data.repository.WeatherRepositoryImpl
import com.kmp.weather.domain.repository.WeatherRepository
import com.kmp.weather.feature.screen.home.viewmodel.HomeViewModel
import com.kmp.weather.feature.screen.search.viewmodel.SearchViewModel
import com.kmp.weather.feature.shared.SharedViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(
            ktorModule,
            viewModelModules,
            repositoryModule,
        )
    }
}

val viewModelModules = module {
    factory { HomeViewModel(weatherRepository = get()) }
    factory { SearchViewModel() }
    factory { SharedViewModel() }
}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}

val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    },
                    contentType = ContentType.Any
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
}