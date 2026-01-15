# US Weather Map

A Kotlin Multiplatform weather application that displays current temperatures for all 50 US state capitals on an interactive map. Built with Compose Multiplatform, targeting Web (WasmJS/JS) and Desktop (JVM).

## Features

- Interactive US map with all 50 states
- Real-time weather data for state capitals via OpenWeather API
- Temperature-based color coding (blue for cold, red for hot)
- Tooltips showing city name, state, and weather details (hover or click/tap to toggle)
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
├── composeApp/
│   └── src/
│       ├── commonMain/          # Shared code across platforms
│       │   ├── kotlin/
│       │   │   ├── ui/          # Compose UI components
│       │   │   │   ├── WeatherMapScreen.kt
│       │   │   │   ├── components/
│       │   │   │   │   ├── USMapView.kt
│       │   │   │   │   └── CapitalMarker.kt
│       │   │   │   └── theme/
│       │   │   │       └── WeatherColors.kt
│       │   │   └── domain/
│       │   │       └── model/
│       │   │           └── StateCapital.kt
│       │   └── composeResources/
│       │       └── drawable/
│       │           └── us_map.xml    # Vector map with state boundaries
│       ├── jvmMain/              # Desktop-specific code
│       └── wasmJsMain/           # Web-specific code
├── tools/
│   ├── build-picker.py           # Generates coordinate picker from map XML
│   └── capital-picker.html       # Interactive tool for positioning capitals
├── vercel.json                   # Vercel deployment configuration
└── vercel-build.sh               # Build script for Vercel
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

### Deploy to Vercel

This project is configured for deployment to Vercel.

1. **Connect your repository to Vercel**
   - Go to [vercel.com](https://vercel.com) and import your GitHub repository
   - Vercel will automatically detect the `vercel.json` configuration

2. **Set the API key environment variable**
   - In Vercel project settings, go to Environment Variables
   - Add `OPENWEATHER_API_KEY` with your OpenWeather API key
   - Get a free API key at [openweathermap.org](https://openweathermap.org/api)

3. **Deploy**
   - Vercel will run `vercel-build.sh` which:
     - Builds the WasmJS production bundle
     - Injects your API key into the HTML
   - Your app will be live at `your-project.vercel.app`

## Configuration

State capitals are defined in `StateCapital.kt` with:
- Normalized map coordinates (0.0-1.0 for `mapX` and `mapY`), manually positioned using the coordinate picker tool
- `isNortheast` flag for smaller markers (22dp vs 28dp) in crowded areas

### Coordinate Picker Tool

If you need to adjust capital marker positions, use the included coordinate picker tool:

1. Open `tools/capital-picker.html` in a browser
2. Select a state from the list on the right
3. Click on the map where the capital should be positioned
4. The state turns green when its coordinate is set
5. Click "Copy All Coordinates" and paste into `StateCapital.kt`

To regenerate the picker tool (if the map SVG changes):
```shell
cd tools
python build-picker.py
```

## Credits

- Map projection and boundaries: [react-usa-map](https://github.com/gabidavila/react-usa-map) (CC BY-SA 3.0)
- Weather data: [OpenWeather API](https://openweathermap.org/api)

## Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/)
- [Kotlin/Wasm](https://kotl.in/wasm/)
