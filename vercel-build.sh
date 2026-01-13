#!/bin/bash
# Vercel build script for US Weather Map

set -e

echo "=== US Weather Map - Vercel Build ==="

# Check for API key
if [ -z "$OPENWEATHER_API_KEY" ]; then
    echo "Warning: OPENWEATHER_API_KEY environment variable not set"
    echo "Using placeholder - set this in Vercel Environment Variables"
    OPENWEATHER_API_KEY="YOUR_API_KEY_HERE"
fi

echo "Step 1: Building WasmJS production bundle..."
./gradlew :composeApp:wasmJsBrowserDistribution --no-daemon

echo "Step 2: Injecting API key into index.html..."
OUTPUT_DIR="composeApp/build/dist/wasmJs/productionExecutable"

# Replace the API key placeholder in the built index.html
sed -i "s/window.OPENWEATHER_API_KEY = \"[^\"]*\"/window.OPENWEATHER_API_KEY = \"$OPENWEATHER_API_KEY\"/" "$OUTPUT_DIR/index.html"

echo "Step 3: Build complete!"
echo "Output directory: $OUTPUT_DIR"
ls -la "$OUTPUT_DIR"
