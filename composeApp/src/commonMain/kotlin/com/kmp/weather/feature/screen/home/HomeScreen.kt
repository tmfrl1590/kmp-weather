package com.kmp.weather.feature.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmp.weather.domain.WeatherRemoteDto
import com.kmp.weather.domain.dto.CityModel
import com.kmp.weather.feature.screen.home.component.CurrentWeatherInfoArea
import com.kmp.weather.feature.screen.home.component.FiveDayForecastArea
import com.kmp.weather.feature.screen.home.component.SearchArea
import com.kmp.weather.feature.screen.home.component.TwoDayThreeHourForecastArea
import com.kmp.weather.feature.screen.home.component.WeatherInfoDetailArea
import com.kmp.weather.feature.shared.ErrorArea
import com.kmp.weather.feature.shared.LoadingArea
import com.kmp.weather.ui.BACKGROUND
import com.kmp.weather.utils.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    selectedCity: CityModel,
    weatherInfoList: UIState<WeatherRemoteDto>,
    onSearchAreaClick: () -> Unit,
) {
    when(weatherInfoList){
        is UIState.Idle -> {}
        is UIState.Loading -> LoadingArea()
        is UIState.Success -> {
            MainScreenContent(
                selectedCity = selectedCity,
                weatherInfo = weatherInfoList.data,
                onSearchAreaClick = onSearchAreaClick
            )
        }
        is UIState.Error -> ErrorArea()
    }
}

@Composable
fun MainScreenContent(
    selectedCity: CityModel,
    weatherInfo: WeatherRemoteDto?,
    onSearchAreaClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND)
                .padding(innerPadding)
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SearchArea(
                onSearchAreaClick = onSearchAreaClick
            )

            CurrentWeatherInfoArea(
                city = selectedCity,
                weather = weatherInfo!!
            )

            TwoDayThreeHourForecastArea(
                weatherInfoList = weatherInfo.list
            )

            FiveDayForecastArea(
                weatherInfoList = weatherInfo.list
            )

            WeatherInfoDetailArea(
                humidity = weatherInfo.list[0].main.humidity,
                clouds = weatherInfo.list[0].clouds.all,
                speed = weatherInfo.list[0].wind.speed,
                pressure = weatherInfo.list[0].main.pressure
            )
        }
    }
}