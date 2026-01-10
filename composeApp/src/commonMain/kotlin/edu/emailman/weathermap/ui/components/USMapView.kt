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

@Composable
fun USMapView(
    capitals: List<CapitalWeather>,
    modifier: Modifier = Modifier
) {
    var mapSize by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier.onSizeChanged { mapSize = it }) {
        Image(
            painter = painterResource(Res.drawable.us_map),
            contentDescription = "US Map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        if (mapSize != IntSize.Zero) {
            capitals.forEach { capitalWeather ->
                CapitalMarker(
                    capitalWeather = capitalWeather,
                    mapSize = mapSize
                )
            }
        }
    }
}
