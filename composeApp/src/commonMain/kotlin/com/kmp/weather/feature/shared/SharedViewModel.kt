package com.kmp.weather.feature.shared

import androidx.lifecycle.ViewModel
import com.kmp.weather.domain.dto.CityModel
import com.kmp.weather.domain.dto.CoordModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel(): ViewModel() {

    val initCity = CityModel(id = 1839726, name = "Asan", country = "KR", coord = CoordModel(127.004173, 36.783611))

    private val _selectedCity = MutableStateFlow(initCity)
    val selectedCity: StateFlow<CityModel> = _selectedCity

    fun setSelectedCity(cityModel: CityModel){
        _selectedCity.value = cityModel
    }
}