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
    val all: List<StateCapital> = listOf(
        // West Coast
        StateCapital("Washington", "Olympia", 47.0379, -122.9007, 0.11f, 0.12f),
        StateCapital("Oregon", "Salem", 44.9429, -123.0351, 0.09f, 0.26f),
        StateCapital("California", "Sacramento", 38.5816, -121.4944, 0.07f, 0.42f),

        // Mountain West
        StateCapital("Nevada", "Carson City", 39.1638, -119.7674, 0.12f, 0.42f),
        StateCapital("Idaho", "Boise", 43.6150, -116.2023, 0.18f, 0.24f),
        StateCapital("Montana", "Helena", 46.5891, -112.0391, 0.26f, 0.13f),
        StateCapital("Wyoming", "Cheyenne", 41.1400, -104.8202, 0.32f, 0.28f),
        StateCapital("Utah", "Salt Lake City", 40.7608, -111.8910, 0.20f, 0.38f),
        StateCapital("Colorado", "Denver", 39.7392, -104.9903, 0.32f, 0.40f),
        StateCapital("Arizona", "Phoenix", 33.4484, -112.0740, 0.18f, 0.55f),
        StateCapital("New Mexico", "Santa Fe", 35.6870, -105.9378, 0.32f, 0.54f),

        // Northern Plains
        StateCapital("North Dakota", "Bismarck", 46.8083, -100.7837, 0.41f, 0.14f),
        StateCapital("South Dakota", "Pierre", 44.3683, -100.3510, 0.42f, 0.25f),
        StateCapital("Nebraska", "Lincoln", 40.8258, -96.6852, 0.45f, 0.36f),
        StateCapital("Kansas", "Topeka", 39.0473, -95.6752, 0.45f, 0.46f),
        StateCapital("Oklahoma", "Oklahoma City", 35.4676, -97.5164, 0.44f, 0.58f),
        StateCapital("Texas", "Austin", 30.2672, -97.7431, 0.40f, 0.75f),

        // Upper Midwest
        StateCapital("Minnesota", "Saint Paul", 44.9537, -93.0900, 0.53f, 0.18f),
        StateCapital("Iowa", "Des Moines", 41.5868, -93.6250, 0.53f, 0.32f),
        StateCapital("Missouri", "Jefferson City", 38.5767, -92.1735, 0.55f, 0.44f),
        StateCapital("Arkansas", "Little Rock", 34.7465, -92.2896, 0.56f, 0.58f),
        StateCapital("Louisiana", "Baton Rouge", 30.4515, -91.1871, 0.57f, 0.72f),

        // Great Lakes
        StateCapital("Wisconsin", "Madison", 43.0731, -89.4012, 0.60f, 0.22f),
        StateCapital("Illinois", "Springfield", 39.7817, -89.6501, 0.60f, 0.38f),
        StateCapital("Michigan", "Lansing", 42.7325, -84.5555, 0.68f, 0.22f),
        StateCapital("Indiana", "Indianapolis", 39.7684, -86.1581, 0.66f, 0.38f),
        StateCapital("Ohio", "Columbus", 39.9612, -82.9988, 0.72f, 0.36f),

        // South Central
        StateCapital("Kentucky", "Frankfort", 38.2009, -84.8733, 0.68f, 0.46f),
        StateCapital("Tennessee", "Nashville", 36.1627, -86.7816, 0.66f, 0.52f),
        StateCapital("Mississippi", "Jackson", 32.2988, -90.1848, 0.60f, 0.64f),
        StateCapital("Alabama", "Montgomery", 32.3792, -86.3077, 0.67f, 0.64f),

        // Southeast
        StateCapital("Georgia", "Atlanta", 33.7490, -84.3880, 0.73f, 0.60f),
        StateCapital("Florida", "Tallahassee", 30.4383, -84.2807, 0.72f, 0.74f),
        StateCapital("South Carolina", "Columbia", 34.0007, -81.0348, 0.80f, 0.58f),
        StateCapital("North Carolina", "Raleigh", 35.7796, -78.6382, 0.84f, 0.52f),

        // Mid-Atlantic
        StateCapital("Virginia", "Richmond", 37.5407, -77.4360, 0.83f, 0.46f),
        StateCapital("West Virginia", "Charleston", 38.3498, -81.6326, 0.76f, 0.42f),
        StateCapital("Maryland", "Annapolis", 38.9784, -76.4922, 0.86f, 0.43f, isNortheast = true),
        StateCapital("Delaware", "Dover", 39.1582, -75.5244, 0.88f, 0.40f, isNortheast = true),
        StateCapital("Pennsylvania", "Harrisburg", 40.2732, -76.8867, 0.83f, 0.35f, isNortheast = true),
        StateCapital("New Jersey", "Trenton", 40.2206, -74.7597, 0.89f, 0.36f, isNortheast = true),
        StateCapital("New York", "Albany", 42.6526, -73.7562, 0.86f, 0.25f, isNortheast = true),

        // New England
        StateCapital("Connecticut", "Hartford", 41.7658, -72.6734, 0.92f, 0.30f, isNortheast = true),
        StateCapital("Rhode Island", "Providence", 41.8240, -71.4128, 0.95f, 0.30f, isNortheast = true),
        StateCapital("Massachusetts", "Boston", 42.3601, -71.0589, 0.94f, 0.26f, isNortheast = true),
        StateCapital("Vermont", "Montpelier", 44.2601, -72.5754, 0.91f, 0.20f, isNortheast = true),
        StateCapital("New Hampshire", "Concord", 43.2081, -71.5376, 0.94f, 0.20f, isNortheast = true),
        StateCapital("Maine", "Augusta", 44.3106, -69.7795, 0.95f, 0.12f, isNortheast = true),

        // Non-contiguous
        StateCapital("Alaska", "Juneau", 58.3019, -134.4197, 0.12f, 0.82f),
        StateCapital("Hawaii", "Honolulu", 21.3070, -157.8584, 0.28f, 0.88f)
    )
}
