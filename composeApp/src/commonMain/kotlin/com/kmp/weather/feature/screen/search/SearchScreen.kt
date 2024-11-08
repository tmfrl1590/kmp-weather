package com.kmp.weather.feature.screen.search

import androidx.compose.runtime.Composable
import com.kmp.weather.domain.dto.CityModel
import com.kmp.weather.utils.FileReadState

@Composable
fun SearchScreen(
    keyword: String,
    filteredCityList: List<CityModel>,
    loadingState: FileReadState<List<CityModel>>,
    onValueChange: (String) -> Unit,
    onCityItemClick: (CityModel) -> Unit,
    onSearch: (String) -> Unit,
) {

}