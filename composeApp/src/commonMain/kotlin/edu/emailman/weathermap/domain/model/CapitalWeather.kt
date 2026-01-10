package edu.emailman.weathermap.domain.model

data class CapitalWeather(
    val capital: StateCapital,
    val weather: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
