package edu.emailman.weathermap.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.emailman.weathermap.ui.components.USMapView
import edu.emailman.weathermap.ui.components.WeatherLegend
import edu.emailman.weathermap.ui.state.WeatherMapUiState
import edu.emailman.weathermap.viewmodel.WeatherMapViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@Composable
fun WeatherMapScreen(viewModel: WeatherMapViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is WeatherMapUiState.Loading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading weather data...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            is WeatherMapUiState.Success -> {
                USMapView(
                    capitals = state.capitals,
                    modifier = Modifier.fillMaxSize()
                )
                WeatherLegend(modifier = Modifier.align(Alignment.BottomEnd))
                Column(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Last Refresh",
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                    state.lastRefresh?.let { instant ->
                        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                        Text(
                            text = formatDate(localDateTime),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = formatTime(localDateTime),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    FilledTonalButton(onClick = { viewModel.refresh() }) {
                        Text("Refresh")
                    }
                }
            }

            is WeatherMapUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

private fun formatDate(dateTime: kotlinx.datetime.LocalDateTime): String {
    val month = dateTime.month.ordinal.plus(1).toString().padStart(2, '0')
    val day = dateTime.day.toString().padStart(2, '0')
    val year = (dateTime.year % 100).toString().padStart(2, '0')
    return "$month/$day/$year"
}

private fun formatTime(dateTime: kotlinx.datetime.LocalDateTime): String {
    val hour24 = dateTime.hour
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }
    val minute = dateTime.minute.toString().padStart(2, '0')
    val amPm = if (hour24 < 12) "AM" else "PM"
    return "$hour12:$minute $amPm"
}
