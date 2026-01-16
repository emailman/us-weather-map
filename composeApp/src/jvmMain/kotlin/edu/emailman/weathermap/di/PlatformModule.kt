package edu.emailman.weathermap.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun createHttpClient(): HttpClient = HttpClient(Java) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}

actual fun getApiKey(): String = System.getenv("OPENWEATHER_API_KEY")
    ?: "f282e6810344a266faf4e7311d63359d" // Fallback for local development
