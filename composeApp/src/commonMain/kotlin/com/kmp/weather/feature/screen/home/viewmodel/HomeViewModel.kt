package com.kmp.weather.feature.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.weather.domain.WeatherRemoteDto
import com.kmp.weather.domain.repository.WeatherRepository
import com.kmp.weather.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _getWeatherInfoList = MutableStateFlow<UIState<WeatherRemoteDto>>(UIState.Idle)
    val getWeatherInfoList: StateFlow<UIState<WeatherRemoteDto>> = _getWeatherInfoList


    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _getWeatherInfoList.value = UIState.Loading
            try {
                val result = weatherRepository.getWeatherInfo(lat, lon)
                _getWeatherInfoList.value = UIState.Success(result)
            } catch (e: Exception){
                _getWeatherInfoList.value = UIState.Error()
            }
        }
    }
}