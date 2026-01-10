package edu.emailman.weathermap.data.repository

import edu.emailman.weathermap.data.api.WeatherApi
import edu.emailman.weathermap.data.cache.WeatherCache
import edu.emailman.weathermap.domain.model.CapitalWeather
import edu.emailman.weathermap.domain.model.StateCapital
import edu.emailman.weathermap.domain.model.StateCapitals
import edu.emailman.weathermap.domain.model.WeatherData
import edu.emailman.weathermap.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val cache: WeatherCache
) : WeatherRepository {

    private val _capitalsWeather = MutableStateFlow(
        StateCapitals.all.map { CapitalWeather(it, null, isLoading = true) }
    )

    override fun getCapitalsWeather(): Flow<List<CapitalWeather>> = _capitalsWeather.asStateFlow()

    override suspend fun refreshWeather() {
        StateCapitals.all.forEach { capital ->
            val result = fetchWeatherForCapital(capital)
            updateCapitalWeather(result)
        }
    }

    private suspend fun fetchWeatherForCapital(capital: StateCapital): CapitalWeather {
        val cached = cache.get(capital.capitalName)
        if (cached != null && !cache.isExpired(capital.capitalName)) {
            return CapitalWeather(capital, cached)
        }

        return try {
            val response = api.getWeather(capital.latitude, capital.longitude)
            val weather = WeatherData(
                temperatureFahrenheit = response.main.temp,
                temperatureCelsius = (response.main.temp - 32) * 5 / 9,
                description = response.weather.firstOrNull()?.description ?: "",
                iconCode = response.weather.firstOrNull()?.icon ?: "",
                humidity = response.main.humidity,
                fetchedAt = Clock.System.now()
            )
            cache.put(capital.capitalName, weather)
            CapitalWeather(capital, weather)
        } catch (e: Exception) {
            CapitalWeather(capital, cache.get(capital.capitalName), error = e.message)
        }
    }

    private fun updateCapitalWeather(updated: CapitalWeather) {
        _capitalsWeather.update { current ->
            current.map { if (it.capital.capitalName == updated.capital.capitalName) updated else it }
        }
    }
}
