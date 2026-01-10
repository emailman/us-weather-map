package edu.emailman.weathermap.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun createHttpClient(): HttpClient = HttpClient(Js) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}

@JsFun("() => window.OPENWEATHER_API_KEY || ''")
private external fun getApiKeyFromWindow(): String

actual fun getApiKey(): String {
    val key = getApiKeyFromWindow()
    if (key.isEmpty()) error("window.OPENWEATHER_API_KEY not set in HTML")
    return key
}
