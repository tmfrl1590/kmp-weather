package com.kmp.weather.feature.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmp.weather.domain.WeatherRemoteDto
import com.kmp.weather.domain.dto.CityModel
import com.kmp.weather.feature.shared.CommonText
import com.kmp.weather.utils.convertTempToText
import com.kmp.weather.utils.convertToCelsiusFromKelvin
import com.kmp.weather.utils.convertToTextFromWeather
import com.kmp.weather.utils.getApiImage
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

// 2번째 데이터를 가져오는 이유 : 0번째는 6시간 전, 1번째는 3시간 전, 2번째는 현재 시간 -> 현재 시간에 가장 가깝기 때문
const val THIRD_ITEM_INDEX = 2

@Composable
fun CurrentWeatherInfoArea(
    city: CityModel,
    weather: WeatherRemoteDto,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonText(
            text = city.name,
            fontSize = 28.sp,
        )
        CommonText(
            modifier = Modifier.testTag("currentTemp"),
            text = convertToCelsiusFromKelvin(weather.list[THIRD_ITEM_INDEX].main.temp),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            CoilImage(
                modifier = Modifier.size(80.dp),
                imageModel = { getApiImage(weather.list[THIRD_ITEM_INDEX].weather[0].icon) },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )

            CommonText(
                modifier = Modifier.testTag("currentWeatherKR"),
                text = convertToTextFromWeather(weather.list[THIRD_ITEM_INDEX].weather[0].main),
                fontSize = 28.sp,
            )
        }

        CommonText(
            text = convertTempToText(
                weather.list[THIRD_ITEM_INDEX].main.tempMin,
                weather.list[THIRD_ITEM_INDEX].main.tempMax
            ),
            fontSize = 20.sp,
        )
    }
}