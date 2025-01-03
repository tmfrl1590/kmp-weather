package com.kmp.weather.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmp.weather.domain.dto.WeatherInfoDto
import com.kmp.weather.feature.shared.CommonText
import com.kmp.weather.ui.COMPONENT_BACKGROUND
import com.kmp.weather.utils.convertToIconNumFromWeather
import com.kmp.weather.utils.convertToMinTempAndMaxTemp
import com.kmp.weather.utils.convertToTextFromWeather
import com.kmp.weather.utils.getApiImage
import com.kmp.weather.utils.groupDailyWeather
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun FiveDayForecastArea(
    weatherInfoList: List<WeatherInfoDto>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp)
            .testTag("fiveDayForecastArea"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = COMPONENT_BACKGROUND
        ),
    ) {
        groupDailyWeather(weatherInfoList).forEachIndexed { _, weatherData ->
            FiveDayForecastItem(
                day = weatherData.date,
                weather = weatherData.weather,
                minTemp = weatherData.minTemp,
                maxTemp = weatherData.maxTemp,
            )
        }
    }
}

@Composable
fun FiveDayForecastItem(
    day: String,
    weather: String,
    minTemp: Double,
    maxTemp: Double,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(COMPONENT_BACKGROUND),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CommonText(
                modifier = Modifier.weight(1f),
                text = day,
                fontSize = 16.sp,
            )

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CommonText(
                    text = convertToTextFromWeather(weather),
                    fontSize = 16.sp,
                )

                CoilImage(
                    modifier = Modifier.size(80.dp),
                    imageModel = { getApiImage(convertToIconNumFromWeather(weather)) },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }

            CommonText(
                modifier = Modifier.weight(2f),
                text = convertToMinTempAndMaxTemp(minTemp = minTemp, maxTemp = maxTemp),
                fontSize = 16.sp,
            )
        }
        HorizontalDivider(color = Color.White, thickness = 0.3.dp)
    }
}


