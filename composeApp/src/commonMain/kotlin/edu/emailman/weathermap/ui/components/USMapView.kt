package edu.emailman.weathermap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import edu.emailman.weathermap.domain.model.CapitalWeather
import org.jetbrains.compose.resources.painterResource
import weathermap.composeapp.generated.resources.Res
import weathermap.composeapp.generated.resources.us_map

// Map intrinsic dimensions from the SVG viewBox
private const val MAP_INTRINSIC_WIDTH = 960f
private const val MAP_INTRINSIC_HEIGHT = 600f
private const val MAP_ASPECT_RATIO = MAP_INTRINSIC_WIDTH / MAP_INTRINSIC_HEIGHT // 1.6

/**
 * Represents the actual rendered bounds of the map image within its container.
 * When ContentScale.Fit is used, the map maintains its aspect ratio and is centered,
 * which may leave padding (letterboxing) if the container has a different aspect ratio.
 */
data class MapBounds(
    val offsetX: Float,   // Left padding (0 if map fills width)
    val offsetY: Float,   // Top padding (0 if map fills height)
    val width: Float,     // Actual rendered width of the map
    val height: Float     // Actual rendered height of the map
)

/**
 * Calculates the actual map bounds within the container after ContentScale.Fit is applied.
 */
private fun calculateMapBounds(containerSize: IntSize): MapBounds {
    val containerWidth = containerSize.width.toFloat()
    val containerHeight = containerSize.height.toFloat()
    val containerAspectRatio = containerWidth / containerHeight

    return if (containerAspectRatio > MAP_ASPECT_RATIO) {
        // Container is wider than map - fit by height, horizontal padding
        val mapHeight = containerHeight
        val mapWidth = mapHeight * MAP_ASPECT_RATIO
        val offsetX = (containerWidth - mapWidth) / 2f
        MapBounds(offsetX, 0f, mapWidth, mapHeight)
    } else {
        // Container is taller than map - fit by width, vertical padding
        val mapWidth = containerWidth
        val mapHeight = mapWidth / MAP_ASPECT_RATIO
        val offsetY = (containerHeight - mapHeight) / 2f
        MapBounds(0f, offsetY, mapWidth, mapHeight)
    }
}

@Composable
fun USMapView(
    capitals: List<CapitalWeather>,
    modifier: Modifier = Modifier
) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier.onSizeChanged { containerSize = it }) {
        Image(
            painter = painterResource(Res.drawable.us_map),
            contentDescription = "US Map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        if (containerSize != IntSize.Zero) {
            val mapBounds = calculateMapBounds(containerSize)

            capitals.forEach { capitalWeather ->
                CapitalMarker(
                    capitalWeather = capitalWeather,
                    mapBounds = mapBounds
                )
            }
        }
    }
}
