package com.kmp.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kmp.weather.feature.screen.home.HomeScreen
import com.kmp.weather.feature.screen.home.viewmodel.HomeViewModel
import com.kmp.weather.feature.screen.search.SearchScreen
import com.kmp.weather.feature.shared.SharedViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavigation(

) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = koinViewModel<SharedViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.Home,
    ){
        composable<Screen.Home> {
            val selectedCity by sharedViewModel.selectedCity.collectAsState()
            val homeViewModel = koinViewModel<HomeViewModel>()
            val weatherInfoList by homeViewModel.getWeatherInfoList.collectAsState()

            LaunchedEffect(key1 = selectedCity) {
                homeViewModel.getWeather(selectedCity.coord.lat, selectedCity.coord.lon)
            }

            HomeScreen(
                selectedCity = selectedCity,
                weatherInfoList = weatherInfoList,
                onSearchAreaClick = { navController.navigate(Screen.Search) }
            )
        }

        composable<Screen.Search> {
            SearchScreen()
        }
    }
}