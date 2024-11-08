package com.kmp.weather.feature.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmp.weather.domain.WeatherInfoDto
import com.kmp.weather.feature.shared.CommonText
import com.kmp.weather.ui.COMPONENT_BACKGROUND
import com.kmp.weather.utils.convertToCelsiusFromKelvin
import com.kmp.weather.utils.convertToKoreanTime
import com.kmp.weather.utils.filterPastWeatherData
import com.kmp.weather.utils.getApiImage
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun TwoDayThreeHourForecastArea(
    weatherInfoList: List<WeatherInfoDto>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .testTag("twoDayThreeHourForecastArea"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = COMPONENT_BACKGROUND
        ),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            contentPadding = PaddingValues(4.dp)
        ) {
            itemsIndexed(
                items = filterPastWeatherData(weatherInfoList),
            ){ _, item ->
                TwoDayThreeHourForecastItem(
                    time = convertToKoreanTime(item.dtTxt),
                    temperature = convertToCelsiusFromKelvin(item.main.temp),
                    imageUrl = getApiImage(item.weather[0].icon),
                )
            }
        }
    }
}

@Composable
fun TwoDayThreeHourForecastItem(
    time: String,
    temperature: String,
    imageUrl: String,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight()
            .padding(12.dp),
    ) {
        CommonText(
            modifier = Modifier.align(CenterHorizontally),
            text = time,
            fontSize = 16.sp
        )
        CommonText(
            modifier = Modifier.align(CenterHorizontally),
            text = temperature,
            fontSize = 16.sp
        )
        CoilImage(
            modifier = Modifier.size(60.dp),
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )
    }
}