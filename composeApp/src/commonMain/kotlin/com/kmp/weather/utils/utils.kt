package com.kmp.weather.utils

import androidx.compose.ui.text.intl.Locale
import com.kmp.weather.domain.WeatherInfoDto
import io.ktor.http.HttpHeaders.Date
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.pow
import kotlin.math.round

// 켈빈 -> 섭씨 변환, 소수점 첫째 자리
/*fun convertToCelsiusFromKelvin(kelvin: Double): String {
    val celsius = kelvin - 273.15
    return "%.1f°".format(celsius)
}*/

fun Double.toFixed(digits: Int): String {
    val multiplier = 10.0.pow(digits)
    val roundedValue = round(this * multiplier) / multiplier
    return roundedValue.toString()
}

fun convertToCelsiusFromKelvin(kelvin: Double): String {
    val celsius = kelvin - 273.15
    return "${celsius.toFixed(1)}°"
}

// weatherIcon 을 받아서 이미지 url로 변환
fun getApiImage(weatherIcon: String): String{
    // weatherIcon 에 n 이 있으면 d로 변경 (아이콘을 주간 아이콘으로 바꾸기 위해)
    val changedWeatherIcon = if(weatherIcon.contains("n")) weatherIcon.replace("n", "d") else weatherIcon
    return "https://openweathermap.org/img/wn/${changedWeatherIcon}@2x.png"
}

fun convertTempToText(minTemp: Double, maxTemp: Double): String{
    return "최소 ${convertToCelsiusFromKelvin(minTemp)}  |  최대 ${convertToCelsiusFromKelvin(maxTemp)}"
}

fun convertToTextFromWeather(weather: String): String{
    when(weather){
        "Thunderstorm" -> return WeatherEnum.THUNDERSTORM.weather
        "Drizzle" -> return WeatherEnum.DRIZZLE.weather
        "Rain" -> return WeatherEnum.RAIN.weather
        "Snow" -> return WeatherEnum.SNOW.weather
        "Atmosphere" -> return WeatherEnum.ATMOSPHERE.weather
        "Clear" -> return WeatherEnum.CLEAR.weather
        "Clouds" -> return WeatherEnum.CLOUDS.weather
        "Mist" -> return WeatherEnum.MIST.weather
        "Smoke" -> return WeatherEnum.SMOKE.weather
        "Haze" -> return WeatherEnum.HAZE.weather
        "Dust" -> return WeatherEnum.DUST.weather
        "Fog" -> return WeatherEnum.FOG.weather
        "Sand" -> return WeatherEnum.SAND.weather
        "Ash" -> return WeatherEnum.ASH.weather
        "Squall" -> return WeatherEnum.SQUALL.weather
        "Tornado" -> return WeatherEnum.TORNADO.weather
        else -> return WeatherEnum.UNDEFINED.weather
    }
}

enum class WeatherEnum(val weather: String){
    THUNDERSTORM("천둥번개"),
    DRIZZLE("이슬비"),
    RAIN("비"),
    SNOW("눈"),
    ATMOSPHERE("대기"),
    CLEAR("맑음"),
    CLOUDS("구름"),
    MIST("안개"),
    SMOKE("연기"),
    HAZE("연무"),
    DUST("먼지"),
    FOG("안개"),
    SAND("모래"),
    ASH("재"),
    SQUALL("돌풍"),
    TORNADO("토네이도"),
    UNDEFINED("알 수 없음")
}


fun filterPastWeatherData(list: List<WeatherInfoDto>): List<WeatherInfoDto> {
    val currentInstant = Clock.System.now()

    // 현재 시간을 기준으로 이후 데이터만 필터링
    return list.filter { data ->
        val dataInstant = try {
            data.dtTxt.toInstant1()
        } catch (e: Exception) {
            null
        }
        dataInstant != null && dataInstant > currentInstant
    }
}

// 문자열을 Instant로 변환하는 확장 함수
fun String.toInstant1(): Instant? {
    return try {
        val dateTime = LocalDateTime.parse(this.replace(" ", "T")) // "yyyy-MM-dd HH:mm:ss" -> "yyyy-MM-ddTHH:mm:ss"
        dateTime.toInstant(TimeZone.of("Asia/Seoul")) // 한국 시간대 기준으로 변환
    } catch (e: Exception) {
        null // 변환 실패 시 null 반환
    }
}

fun convertToMinTempAndMaxTemp(minTemp: Double, maxTemp: Double): String{
    return "최소 ${convertToCelsiusFromKelvin(minTemp)}  최대 ${convertToCelsiusFromKelvin(maxTemp)}"
}

fun convertToIconNumFromWeather(weather: String): String{
    return when(weather){
        "Clear" -> "01d"
        "Clouds" -> "02d"
        "Drizzle" -> "09d"
        "Rain" -> "10d"
        "Thunderstorm" -> "11d"
        "Snow" -> "13d"
        else -> "50d"
    }
}


fun convertToKoreanTime(dateStr: String, currentDate: Instant = Clock.System.now()): String {
    // 날짜 문자열을 Instant로 변환
    val inputDateTime = try {
        LocalDateTime.parse(dateStr.replace(" ", "T")) // "yyyy-MM-dd HH:mm:ss" -> "yyyy-MM-ddTHH:mm:ss"
    } catch (e: Exception) {
        return ""
    }

    // 한국 시간대로 변환
    val seoulTimeZone = TimeZone.of("Asia/Seoul")
    val inputDateSeoul = inputDateTime.toInstant(seoulTimeZone).toLocalDateTime(seoulTimeZone)
    val currentDateSeoul = currentDate.toLocalDateTime(seoulTimeZone)

    // 현재 시간을 기준으로 3시간 단위로 반올림
    val currentHour = currentDateSeoul.hour
    val roundedHour = (currentHour / 3) * 3 + if (currentHour % 3 >= 1) 3 else 0

    // inputDate의 시간 추출
    val inputHour = inputDateSeoul.hour

    // 반올림된 현재 시각과 inputDate의 시간이 같으면 "지금" 반환
    if (inputHour == roundedHour) {
        return "지금"
    }

    // 오전/오후 및 12시간 형식 변환
    val period = if (inputHour < 12) "오전" else "오후"
    val formattedHour = if (inputHour % 12 == 0) 12 else inputHour % 12

    return "$period ${formattedHour}시"
}


data class WeatherData(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weather: String // 대표 날씨 추가
)

fun groupDailyWeather(dataList: List<WeatherInfoDto>): List<WeatherData> {
    val groupedData = mutableMapOf<String, Pair<Double, Double>>() // 날짜별 최고/최저 온도 저장
    val weatherCount = mutableMapOf<String, MutableMap<String, Int>>() // 날짜별 날씨 카운트 저장
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    dataList.forEach { data ->
        val dtTxt = data.dtTxt
        val main = data.main
        val tempMax = main.tempMax
        val tempMin = main.tempMin
        val weather = data.weather.firstOrNull()?.main ?: "Clear"

        // 문자열을 LocalDateTime으로 파싱하고, 한국 시간대로 변환
        val dateTime = try {
            LocalDateTime.parse(dtTxt.replace(" ", "T"))
        } catch (e: Exception) {
            return@forEach
        }
        val dayOfWeek = dateTime.toInstant(TimeZone.of("Asia/Seoul")).toLocalDateTime(TimeZone.of("Asia/Seoul")).date.dayOfWeek
        val day = daysOfWeek[dayOfWeek.ordinal] // 요일을 한글로 변환

        // 그룹화하여 최고/최저 온도 업데이트
        val currentTemps = groupedData.getOrElse(day) { Pair(tempMax, tempMin) }
        groupedData[day] = Pair(
            maxOf(currentTemps.first, tempMax),
            minOf(currentTemps.second, tempMin)
        )

        // 날씨 카운트 업데이트
        val dayWeatherCount = weatherCount.getOrPut(day) { mutableMapOf() }
        dayWeatherCount[weather] = dayWeatherCount.getOrElse(weather) { 0 } + 1
    }

    // 오늘의 요일 및 요일 순서 설정
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Asia/Seoul")).date
    val todayDayOfWeek = currentDate.dayOfWeek
    val today = daysOfWeek[todayDayOfWeek.ordinal]
    val startIndex = daysOfWeek.indexOf(today)

    // 오늘부터 요일 순서로 정렬하여 반환
    val sortedDays = if (startIndex != -1) {
        daysOfWeek.subList(startIndex, daysOfWeek.size) + daysOfWeek.subList(0, startIndex)
    } else {
        daysOfWeek
    }

    return sortedDays.mapNotNull { day ->
        groupedData[day]?.let { temps ->
            val mostCommonWeather = weatherCount[day]?.maxByOrNull { it.value }?.key ?: "Clear"
            WeatherData(
                date = if (day == today) "오늘" else day,
                maxTemp = temps.first,
                minTemp = temps.second,
                weather = mostCommonWeather,
            )
        }
    }
}