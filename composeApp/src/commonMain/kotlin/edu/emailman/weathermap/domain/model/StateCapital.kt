package edu.emailman.weathermap.domain.model

data class StateCapital(
    val stateName: String,
    val capitalName: String,
    val latitude: Double,
    val longitude: Double,
    val mapX: Float,
    val mapY: Float,
    val isNortheast: Boolean = false // Flag for smaller markers in crowded NE region
)

object StateCapitals {
    // Coordinates manually positioned using capital-picker.html tool
    // Map viewport: 960x600
    // Coordinates normalized to 0.0-1.0 range (mapX = x/960, mapY = y/600)
    val all: List<StateCapital> = listOf(
        // West Coast
        StateCapital("Washington", "Olympia", 47.0379, -122.9007, 0.100f, 0.069f),
        StateCapital("Oregon", "Salem", 44.9429, -123.0351, 0.060f, 0.178f),
        StateCapital("California", "Sacramento", 38.5816, -121.4944, 0.056f, 0.401f),

        // Mountain West
        StateCapital("Nevada", "Carson City", 39.1638, -119.7674, 0.092f, 0.371f),
        StateCapital("Idaho", "Boise", 43.6150, -116.2023, 0.175f, 0.246f),
        StateCapital("Montana", "Helena", 46.5891, -112.0391, 0.249f, 0.188f),
        StateCapital("Wyoming", "Cheyenne", 41.1400, -104.8202, 0.342f, 0.368f),
        StateCapital("Utah", "Salt Lake City", 40.7608, -111.8910, 0.227f, 0.368f),
        StateCapital("Colorado", "Denver", 39.7392, -104.9903, 0.342f, 0.423f),
        StateCapital("Arizona", "Phoenix", 33.4484, -112.0740, 0.210f, 0.611f),
        StateCapital("New Mexico", "Santa Fe", 35.6870, -105.9378, 0.317f, 0.579f),

        // Northern Plains
        StateCapital("North Dakota", "Bismarck", 46.8083, -100.7837, 0.436f, 0.171f),
        StateCapital("South Dakota", "Pierre", 44.3683, -100.3510, 0.439f, 0.258f),
        StateCapital("Nebraska", "Lincoln", 40.8258, -96.6852, 0.491f, 0.408f),
        StateCapital("Kansas", "Topeka", 39.0473, -95.6752, 0.506f, 0.464f),
        StateCapital("Oklahoma", "Oklahoma City", 35.4676, -97.5164, 0.478f, 0.578f),
        StateCapital("Texas", "Austin", 30.2672, -97.7431, 0.469f, 0.786f),

        // Upper Midwest
        StateCapital("Minnesota", "Saint Paul", 44.9537, -93.0900, 0.546f, 0.233f),
        StateCapital("Iowa", "Des Moines", 41.5868, -93.6250, 0.557f, 0.374f),
        StateCapital("Missouri", "Jefferson City", 38.5767, -92.1735, 0.559f, 0.481f),
        StateCapital("Arkansas", "Little Rock", 34.7465, -92.2896, 0.575f, 0.621f),
        StateCapital("Louisiana", "Baton Rouge", 30.4515, -91.1871, 0.591f, 0.796f),

        // Great Lakes
        StateCapital("Wisconsin", "Madison", 43.0731, -89.4012, 0.624f, 0.304f),
        StateCapital("Illinois", "Springfield", 39.7817, -89.6501, 0.620f, 0.426f),
        StateCapital("Michigan", "Lansing", 42.7325, -84.5555, 0.694f, 0.316f),
        StateCapital("Indiana", "Indianapolis", 39.7684, -86.1581, 0.674f, 0.428f),
        StateCapital("Ohio", "Columbus", 39.9612, -82.9988, 0.732f, 0.394f),

        // South Central
        StateCapital("Kentucky", "Frankfort", 38.2009, -84.8733, 0.689f, 0.504f),
        StateCapital("Tennessee", "Nashville", 36.1627, -86.7816, 0.683f, 0.573f),
        StateCapital("Mississippi", "Jackson", 32.2988, -90.1848, 0.621f, 0.686f),
        StateCapital("Alabama", "Montgomery", 32.3792, -86.3077, 0.686f, 0.696f),

        // Southeast
        StateCapital("Georgia", "Atlanta", 33.7490, -84.3880, 0.727f, 0.644f),
        StateCapital("Florida", "Tallahassee", 30.4383, -84.2807, 0.729f, 0.766f),
        StateCapital("South Carolina", "Columbia", 34.0007, -81.0348, 0.792f, 0.624f),
        StateCapital("North Carolina", "Raleigh", 35.7796, -78.6382, 0.828f, 0.533f),

        // Mid-Atlantic
        StateCapital("Virginia", "Richmond", 37.5407, -77.4360, 0.828f, 0.471f),
        StateCapital("West Virginia", "Charleston", 38.3498, -81.6326, 0.768f, 0.449f),
        StateCapital("Maryland", "Annapolis", 38.9784, -76.4922, 0.852f, 0.413f, isNortheast = true),
        StateCapital("Delaware", "Dover", 39.1582, -75.5244, 0.860f, 0.401f, isNortheast = true),
        StateCapital("Pennsylvania", "Harrisburg", 40.2732, -76.8867, 0.825f, 0.366f, isNortheast = true),
        StateCapital("New Jersey", "Trenton", 40.2206, -74.7597, 0.863f, 0.344f, isNortheast = true),
        StateCapital("New York", "Albany", 42.6526, -73.7562, 0.868f, 0.264f, isNortheast = true),

        // New England
        StateCapital("Connecticut", "Hartford", 41.7658, -72.6734, 0.897f, 0.293f, isNortheast = true),
        StateCapital("Rhode Island", "Providence", 41.8240, -71.4128, 0.915f, 0.284f, isNortheast = true),
        StateCapital("Massachusetts", "Boston", 42.3601, -71.0589, 0.919f, 0.261f, isNortheast = true),
        StateCapital("Vermont", "Montpelier", 44.2601, -72.5754, 0.885f, 0.216f, isNortheast = true),
        StateCapital("New Hampshire", "Concord", 43.2081, -71.5376, 0.906f, 0.228f, isNortheast = true),
        StateCapital("Maine", "Augusta", 44.3106, -69.7795, 0.929f, 0.161f, isNortheast = true),

        // Non-contiguous (inset positions)
        StateCapital("Alaska", "Juneau", 58.3019, -134.4197, 0.200f, 0.948f),
        StateCapital("Hawaii", "Honolulu", 21.3070, -157.8584, 0.291f, 0.879f)
    )
}
