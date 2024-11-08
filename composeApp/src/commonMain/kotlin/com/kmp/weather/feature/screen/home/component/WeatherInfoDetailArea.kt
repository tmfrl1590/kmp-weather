package com.kmp.weather.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmp.weather.feature.shared.CommonText
import com.kmp.weather.ui.COMPONENT_BACKGROUND

@Composable
fun WeatherInfoDetailArea(
    humidity: Int,
    clouds: Int,
    speed: Double,
    pressure: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem1").weight(1f),
            title = "습도",
            data = "$humidity%"
        )
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem2").weight(1f),
            title = "구름",
            data = "$clouds%"
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem3").weight(1f),
            title = "풍속",
            data = "$speed m/s"
        )
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem4").weight(1f),
            title = "기압",
            data = "$pressure hPa"
        )
    }
}

@Composable
fun WeatherInfoDetailItem(
    modifier: Modifier = Modifier,
    title: String,
    data: String,
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(COMPONENT_BACKGROUND)
                .padding(12.dp),
        ){
            Column {
                CommonText(
                    text = title,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                CommonText(
                    modifier = modifier,
                    text = data,
                    fontSize = 32.sp,
                )
            }
        }
    }
}