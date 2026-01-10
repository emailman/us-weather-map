# US Weather Map

A Kotlin Multiplatform weather application that displays current temperatures for all 50 US state capitals on an interactive map. Built with Compose Multiplatform, targeting Web (WasmJS/JS) and Desktop (JVM).

## Features

- Interactive US map with all 50 states
- Real-time weather data for state capitals via OpenWeather API
- Temperature-based color coding (blue for cold, red for hot)
- Hover tooltips showing city name, state, and weather details
- Responsive design with smaller markers for crowded Northeast region

## Tech Stack

- **Kotlin**: 2.3.0
- **Compose Multiplatform**: 1.9.3
- **Ktor Client**: 3.1.3 (weather API calls)
- **kotlinx-datetime**: 0.7.1
- **kotlinx-serialization**: 1.8.1

## Map Data

The US map uses geographically accurate state boundaries derived from the [react-usa-map](https://github.com/gabidavila/react-usa-map) project, which provides:

- **Albers USA projection** - The standard equal-area conic projection for US maps
- **Accurate state boundaries** - Derived from authoritative geographic data
- **Optimized paths** - Simplified for web rendering while maintaining visual accuracy
- **Proper insets** - Alaska and Hawaii positioned in the lower-left corner

The map data is stored as an Android Vector Drawable in `composeApp/src/commonMain/composeResources/drawable/us_map.xml` and renders at 960x600 viewport.

## Project Structure

```
composeApp/
├── src/
│   ├── commonMain/          # Shared code across platforms
│   │   ├── kotlin/
│   │   │   ├── ui/          # Compose UI components
│   │   │   │   ├── WeatherMapScreen.kt
│   │   │   │   ├── components/
│   │   │   │   │   ├── USMapView.kt
│   │   │   │   │   └── CapitalMarker.kt
│   │   │   │   └── theme/
│   │   │   │       └── WeatherColors.kt
│   │   │   └── domain/
│   │   │       └── model/
│   │   │           └── StateCapital.kt
│   │   └── composeResources/
│   │       └── drawable/
│   │           └── us_map.xml    # Vector map with state boundaries
│   ├── jvmMain/              # Desktop-specific code
│   └── wasmJsMain/           # Web-specific code
```

## Build and Run

### Desktop (JVM)

Requires `OPENWEATHER_API_KEY` environment variable.

```shell
# macOS/Linux
./gradlew :composeApp:run

# Windows
.\gradlew.bat :composeApp:run
```

### Web (WasmJS - Recommended)

```shell
# macOS/Linux
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Windows
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

Then open http://localhost:8080 in a modern browser.

### Web (JS - Legacy browsers)

```shell
# macOS/Linux
./gradlew :composeApp:jsBrowserDevelopmentRun

# Windows
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

## Configuration

State capitals are defined in `StateCapital.kt` with:
- Normalized map coordinates (0.0-1.0 for `mapX` and `mapY`)
- `isNortheast` flag for smaller markers (22dp vs 28dp) in crowded areas

## Credits

- Map projection and boundaries: [react-usa-map](https://github.com/gabidavila/react-usa-map) (CC BY-SA 3.0)
- Weather data: [OpenWeather API](https://openweathermap.org/api)

## Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/)
- [Kotlin/Wasm](https://kotl.in/wasm/)
