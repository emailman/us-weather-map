package edu.emailman.weathermap.di

import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient

expect fun getApiKey(): String
