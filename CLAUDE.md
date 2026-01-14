# US Weather Map - Project Memory

## Project Overview
A Kotlin Multiplatform weather application that displays current temperatures for all 50 US state capitals on an interactive map. Built with Compose Multiplatform, targeting JS/Browser, JVM/Desktop, and WasmJS.

## Tech Stack
- **Kotlin**: 2.3.0
- **Compose Multiplatform**: 1.9.3
- **Ktor Client**: 3.1.3 (for weather API calls)
- **kotlinx-datetime**: 0.7.1 (uses kotlin.time.Clock/Instant from stdlib)
- **kotlinx-serialization**: 1.8.1

## Key Architecture
- `composeApp/` - Main application module
- `commonMain/` - Shared code across platforms
- Vector drawable map at `composeResources/drawable/us_map.xml`
- State capitals defined in `domain/model/StateCapital.kt` with normalized map coordinates

## Important Notes

### kotlinx-datetime 0.7.x Migration
The project uses Kotlin 2.3.0 which moved `Clock` and `Instant` to the standard library. Imports must use:
```kotlin
import kotlin.time.Clock
import kotlin.time.Instant
```
NOT `kotlinx.datetime.Clock` or `kotlinx.datetime.Instant`.

### Map Coordinates
- State capitals use normalized coordinates (0.0-1.0) for `mapX` and `mapY`
- Northeast states have `isNortheast = true` flag for smaller markers (22dp vs 28dp)
- Map viewport is 960x600

### Running the App
```bash
# WASM Browser (development) - runs at localhost:8083
./gradlew wasmJsBrowserDevelopmentRun

# JS Browser (development) - runs at localhost:8080
./gradlew jsBrowserDevelopmentRun

# JVM Desktop
./gradlew run
```

## Recent Changes (Jan 2026)
1. Fixed IrLinkageError by upgrading kotlinx-datetime 0.6.2 → 0.7.1
2. Updated imports from `kotlinx.datetime.*` to `kotlin.time.*`
3. Added proper US map SVG with state boundaries
4. Added hover tooltips showing city name, state, and weather details
5. Implemented smaller markers for crowded Northeast region
6. **Jan 10, 2026**: Replaced state boundaries with geographically accurate Albers USA projection paths
7. **Jan 11, 2026**: Fixed state capital marker positions to align with Albers USA projection
8. **Jan 12, 2026**: Recalculated all marker positions using proper Albers USA projection formula
9. **Jan 14, 2026**: Fixed Refresh button, timestamp display, and JVM window size

## Completed Work

### Bug Fixes and UI Improvements (Jan 14, 2026)
- **Status**: Complete
- **What was done**:
  1. **WASM API Key**: Added OpenWeatherMap API key to `webMain/resources/index.html` for local development (was showing "Failed to load weather data")
  2. **Last Refresh Timestamp**: Fixed race condition in `WeatherMapViewModel.kt` where timestamp was set after API call, causing it to be null when state updated. Now sets `lastRefreshTime` before calling `refreshWeather()`
  3. **Refresh Button**: Added `forceRefresh` parameter to `WeatherRepository.refreshWeather()`. When true, clears cache before fetching. Manual Refresh button now bypasses 1-hour cache
  4. **JVM Window Size**: Widened from 1024dp to 1300dp for better map visibility
- **Files modified**:
  - `WeatherRepository.kt`: Added `forceRefresh: Boolean = false` parameter
  - `WeatherRepositoryImpl.kt`: Clears cache when `forceRefresh = true`
  - `WeatherMapViewModel.kt`: Fixed timestamp race condition, uses `forceRefresh = true` for manual refresh
  - `main.kt` (jvmMain): Window width 1024dp → 1300dp
  - `index.html` (webMain): Added API key for local WASM development

### State Capital Marker Alignment (Jan 12, 2026)
- **Status**: Complete
- **What was done**: Recalculated all 50 state capital marker positions using the Albers USA projection formula
- **Method**: Created Python script (`tools/calculate-coordinates.py`) implementing the same projection as D3.js `geoAlbersUsa()`
- **Projection parameters**: Scale=1070, Translate=[480, 300] for 960×600 viewport
- **Key changes**:
  - All continental US capitals now use mathematically correct projected coordinates
  - Alaska and Hawaii insets positioned based on SVG analysis
  - Coordinates normalized to 0.0-1.0 range
- **Files**:
  - `StateCapital.kt`: Updated all mapX/mapY values
  - `tools/calculate-coordinates.py`: Projection calculation utility

### State Border Accuracy (Jan 10, 2026)
- **Status**: Complete
- **What was done**: Replaced hand-drawn state boundaries with accurate geographic data
- **Data source**: [react-usa-map](https://github.com/gabidavila/react-usa-map) - uses Albers USA projection derived from authoritative geographic data (Wikimedia Commons, CC BY-SA 3.0)
- **Features**:
  - All 50 states with accurate boundaries
  - Albers USA equal-area conic projection (standard for US maps)
  - Alaska and Hawaii as proper insets in lower-left
  - Multi-polygon support (Michigan peninsulas, California islands, etc.)
  - Optimized paths for web rendering

### Environment Setup
- **Required**: `OPENWEATHER_API_KEY` environment variable for JVM Desktop version
- **WasmJS/JS versions**: Run at http://localhost:8080
- The WasmJS build was successfully compiled and served

## File Locations
- Main screen: `ui/WeatherMapScreen.kt`
- Map component: `ui/components/USMapView.kt`
- Marker component: `ui/components/CapitalMarker.kt`
- State data: `domain/model/StateCapital.kt`
- Weather colors: `ui/theme/WeatherColors.kt`
- Version catalog: `gradle/libs.versions.toml`
