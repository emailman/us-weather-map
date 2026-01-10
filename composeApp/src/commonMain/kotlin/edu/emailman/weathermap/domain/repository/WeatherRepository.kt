package edu.emailman.weathermap.domain.repository

import edu.emailman.weathermap.domain.model.CapitalWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCapitalsWeather(): Flow<List<CapitalWeather>>
    suspend fun refreshWeather()
}
