package edu.emailman.weathermap.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherResponse(
    val main: MainData,
    val weather: List<WeatherInfo>,
    val name: String
)

@Serializable
data class MainData(
    val temp: Double,
    val humidity: Int,
    @SerialName("feels_like") val feelsLike: Double
)

@Serializable
data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
