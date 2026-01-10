package edu.emailman.weathermap.ui.state

import edu.emailman.weathermap.domain.model.CapitalWeather

sealed class WeatherMapUiState {
    data object Loading : WeatherMapUiState()
    data class Success(val capitals: List<CapitalWeather>) : WeatherMapUiState()
    data class Error(val message: String) : WeatherMapUiState()
}
