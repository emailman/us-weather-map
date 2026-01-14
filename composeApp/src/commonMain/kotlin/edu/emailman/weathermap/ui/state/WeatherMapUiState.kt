package edu.emailman.weathermap.ui.state

import edu.emailman.weathermap.domain.model.CapitalWeather
import kotlin.time.Instant

sealed class WeatherMapUiState {
    data object Loading : WeatherMapUiState()
    data class Success(
        val capitals: List<CapitalWeather>,
        val lastRefresh: Instant? = null
    ) : WeatherMapUiState()
    data class Error(val message: String) : WeatherMapUiState()
}
