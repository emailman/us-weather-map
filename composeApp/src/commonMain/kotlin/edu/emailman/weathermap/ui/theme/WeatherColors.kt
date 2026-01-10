package edu.emailman.weathermap.ui.theme

import androidx.compose.ui.graphics.Color

fun temperatureToColor(fahrenheit: Double): Color {
    return when {
        fahrenheit < 32 -> Color(0xFF2196F3)  // Blue (freezing)
        fahrenheit < 50 -> Color(0xFF03A9F4)  // Light Blue
        fahrenheit < 65 -> Color(0xFF4CAF50)  // Green
        fahrenheit < 80 -> Color(0xFFFFEB3B)  // Yellow
        fahrenheit < 95 -> Color(0xFFFF9800)  // Orange
        else -> Color(0xFFF44336)             // Red (hot)
    }
}

val temperatureLegend = listOf(
    "< 32°F" to Color(0xFF2196F3),
    "32-50°F" to Color(0xFF03A9F4),
    "50-65°F" to Color(0xFF4CAF50),
    "65-80°F" to Color(0xFFFFEB3B),
    "80-95°F" to Color(0xFFFF9800),
    "> 95°F" to Color(0xFFF44336)
)
