package edu.emailman.weathermap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.emailman.weathermap.domain.repository.WeatherRepository
import edu.emailman.weathermap.ui.state.WeatherMapUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

class WeatherMapViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherMapUiState>(WeatherMapUiState.Loading)
    val uiState: StateFlow<WeatherMapUiState> = _uiState.asStateFlow()

    private var refreshJob: Job? = null
    private var lastRefreshTime: Instant? = null

    init {
        observeWeatherData()
        startPeriodicRefresh()
        loadInitialData()
    }

    private fun observeWeatherData() {
        viewModelScope.launch {
            repository.getCapitalsWeather().collect { capitals ->
                val hasAnyData = capitals.any { it.weather != null }
                val allLoading = capitals.all { it.isLoading && it.weather == null }

                _uiState.value = when {
                    allLoading -> WeatherMapUiState.Loading
                    hasAnyData -> WeatherMapUiState.Success(capitals, lastRefreshTime)
                    else -> WeatherMapUiState.Error("Failed to load weather data")
                }
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            lastRefreshTime = Clock.System.now()
            repository.refreshWeather()
        }
    }

    private fun startPeriodicRefresh() {
        refreshJob = viewModelScope.launch {
            while (isActive) {
                delay(1.hours)
                lastRefreshTime = Clock.System.now()
                repository.refreshWeather()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            lastRefreshTime = Clock.System.now()
            repository.refreshWeather(forceRefresh = true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }
}
