package edu.emailman.weathermap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import edu.emailman.weathermap.domain.model.CapitalWeather
import edu.emailman.weathermap.ui.theme.temperatureToColor

@Composable
fun BoxScope.CapitalMarker(
    capitalWeather: CapitalWeather,
    mapBounds: MapBounds,
    modifier: Modifier = Modifier
) {
    val capital = capitalWeather.capital
    val weather = capitalWeather.weather
    val density = LocalDensity.current

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Calculate pixel position accounting for map bounds and offset
    // mapX/mapY are normalized coordinates (0.0-1.0) relative to the map's intrinsic size
    val xPx = (mapBounds.offsetX + capital.mapX * mapBounds.width).toInt()
    val yPx = (mapBounds.offsetY + capital.mapY * mapBounds.height).toInt()

    // Use smaller markers for the crowded Northeast region
    val markerSize = if (capital.isNortheast) 22.dp else 28.dp
    val fontSize = if (capital.isNortheast) 8.sp else 10.sp
    val halfMarker = markerSize / 2

    with(density) {
        Box(
            modifier = modifier
                .offset(x = xPx.toDp() - halfMarker, y = yPx.toDp() - halfMarker)
                .zIndex(if (isHovered) 100f else 1f)
                .hoverable(interactionSource = interactionSource),
            contentAlignment = Alignment.Center
        ) {
            // Temperature marker circle
            Box(
                modifier = Modifier
                    .size(markerSize)
                    .shadow(if (isHovered) 8.dp else 2.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        when {
                            capitalWeather.isLoading -> Color.Gray
                            capitalWeather.error != null && weather == null -> Color.Red.copy(alpha = 0.7f)
                            weather != null -> temperatureToColor(weather.temperatureFahrenheit)
                            else -> Color.Gray
                        }
                    )
                    .border(
                        width = if (isHovered) 2.dp else 1.dp,
                        color = if (isHovered) Color.White else Color.White.copy(alpha = 0.8f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (weather != null) {
                    Text(
                        text = "${weather.temperatureFahrenheit.toInt()}°",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                }
            }

            // Tooltip on hover
            if (isHovered && weather != null) {
                Surface(
                    modifier = Modifier
                        .offset(y = -(markerSize + 8.dp))
                        .shadow(4.dp, RoundedCornerShape(6.dp)),
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF2D3748),
                    contentColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = capital.capitalName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = capital.stateName,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.LightGray
                            ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${weather.temperatureFahrenheit.toInt()}°F / ${weather.temperatureCelsius.toInt()}°C",
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                        if (weather.description.isNotEmpty()) {
                            Text(
                                text = weather.description.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.LightGray,
                                    fontSize = 10.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
