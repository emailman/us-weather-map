package edu.emailman.weathermap.data.cache

import edu.emailman.weathermap.domain.model.WeatherData
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

class WeatherCache {
    private val cache = mutableMapOf<String, WeatherData>()
    private val cacheExpiry = 1.hours

    fun get(capitalName: String): WeatherData? = cache[capitalName]

    fun put(capitalName: String, data: WeatherData) {
        cache[capitalName] = data
    }

    fun isExpired(capitalName: String): Boolean {
        val data = cache[capitalName] ?: return true
        val age = Clock.System.now() - data.fetchedAt
        return age > cacheExpiry
    }

    fun clear() {
        cache.clear()
    }
}
