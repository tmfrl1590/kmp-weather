package com.kmp.weather.feature.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmp.weather.domain.dto.CityModel
import com.kmp.weather.feature.screen.search.component.CityListArea
import com.kmp.weather.feature.screen.search.component.SearchBarArea
import com.kmp.weather.feature.shared.ErrorArea
import com.kmp.weather.feature.shared.LoadingArea
import com.kmp.weather.ui.BACKGROUND
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
    when (loadingState) {
        is FileReadState.Idle -> {}
        is FileReadState.Loading -> { LoadingArea() }
        is FileReadState.Success -> {
            SearchScreenContent(
                keyword = keyword,
                filteredCityList = filteredCityList,
                onValueChange = onValueChange,
                onCityItemClick = { onCityItemClick(it) },
                onSearch = { onSearch(keyword) }
            )
        }
        is FileReadState.Error -> ErrorArea()
    }
}

@Composable
fun SearchScreenContent(
    keyword: String,
    filteredCityList: List<CityModel>,
    onValueChange: (String) -> Unit,
    onCityItemClick: (CityModel) -> Unit,
    onSearch: (String) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND)
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            SearchBarArea(
                keyword = keyword,
                onValueChange = onValueChange,
                onSearch = onSearch,
            )

            CityListArea(
                filteredCityList = filteredCityList,
                onCityItemClick = onCityItemClick
            )
        }
    }
}