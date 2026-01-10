package edu.emailman.weathermap

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.emailman.weathermap.data.api.WeatherApi
import edu.emailman.weathermap.data.cache.WeatherCache
import edu.emailman.weathermap.data.repository.WeatherRepositoryImpl
import edu.emailman.weathermap.di.createHttpClient
import edu.emailman.weathermap.di.getApiKey
import edu.emailman.weathermap.ui.WeatherMapScreen
import edu.emailman.weathermap.viewmodel.WeatherMapViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = viewModel {
            val httpClient = createHttpClient()
            val apiKey = getApiKey()
            val api = WeatherApi(httpClient, apiKey)
            val cache = WeatherCache()
            val repository = WeatherRepositoryImpl(api, cache)
            WeatherMapViewModel(repository)
        }

        WeatherMapScreen(viewModel)
    }
}