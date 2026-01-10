package edu.emailman.weathermap.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApi(
    private val client: HttpClient,
    private val apiKey: String
) {
    suspend fun getWeather(lat: Double, lon: Double): OpenWeatherResponse {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("appid", apiKey)
            parameter("units", "imperial")
        }.body()
    }
}
