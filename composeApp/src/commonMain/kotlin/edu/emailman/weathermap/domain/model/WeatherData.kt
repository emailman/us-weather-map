package edu.emailman.weathermap.domain.model

import kotlin.time.Instant

data class WeatherData(
    val temperatureFahrenheit: Double,
    val temperatureCelsius: Double,
    val description: String,
    val iconCode: String,
    val humidity: Int,
    val fetchedAt: Instant
)
