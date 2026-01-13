// D3.js Albers USA projection calculator for US state capitals
// Run: npm install d3-geo && node calculate-coordinates.js

const d3 = require('d3-geo');

// Create the Albers USA projection with standard parameters for 960x600 viewport
const projection = d3.geoAlbersUsa()
    .scale(1070)
    .translate([480, 300]);

// All 50 US state capitals with their geographic coordinates
// Source: StateCapital.kt lat/long values
const capitals = [
    // West Coast
    { state: "Washington", capital: "Olympia", lat: 47.0379, lon: -122.9007 },
    { state: "Oregon", capital: "Salem", lat: 44.9429, lon: -123.0351 },
    { state: "California", capital: "Sacramento", lat: 38.5816, lon: -121.4944 },

    // Mountain West
    { state: "Nevada", capital: "Carson City", lat: 39.1638, lon: -119.7674 },
    { state: "Idaho", capital: "Boise", lat: 43.6150, lon: -116.2023 },
    { state: "Montana", capital: "Helena", lat: 46.5891, lon: -112.0391 },
    { state: "Wyoming", capital: "Cheyenne", lat: 41.1400, lon: -104.8202 },
    { state: "Utah", capital: "Salt Lake City", lat: 40.7608, lon: -111.8910 },
    { state: "Colorado", capital: "Denver", lat: 39.7392, lon: -104.9903 },
    { state: "Arizona", capital: "Phoenix", lat: 33.4484, lon: -112.0740 },
    { state: "New Mexico", capital: "Santa Fe", lat: 35.6870, lon: -105.9378 },

    // Northern Plains
    { state: "North Dakota", capital: "Bismarck", lat: 46.8083, lon: -100.7837 },
    { state: "South Dakota", capital: "Pierre", lat: 44.3683, lon: -100.3510 },
    { state: "Nebraska", capital: "Lincoln", lat: 40.8258, lon: -96.6852 },
    { state: "Kansas", capital: "Topeka", lat: 39.0473, lon: -95.6752 },
    { state: "Oklahoma", capital: "Oklahoma City", lat: 35.4676, lon: -97.5164 },
    { state: "Texas", capital: "Austin", lat: 30.2672, lon: -97.7431 },

    // Upper Midwest
    { state: "Minnesota", capital: "Saint Paul", lat: 44.9537, lon: -93.0900 },
    { state: "Iowa", capital: "Des Moines", lat: 41.5868, lon: -93.6250 },
    { state: "Missouri", capital: "Jefferson City", lat: 38.5767, lon: -92.1735 },
    { state: "Arkansas", capital: "Little Rock", lat: 34.7465, lon: -92.2896 },
    { state: "Louisiana", capital: "Baton Rouge", lat: 30.4515, lon: -91.1871 },

    // Great Lakes
    { state: "Wisconsin", capital: "Madison", lat: 43.0731, lon: -89.4012 },
    { state: "Illinois", capital: "Springfield", lat: 39.7817, lon: -89.6501 },
    { state: "Michigan", capital: "Lansing", lat: 42.7325, lon: -84.5555 },
    { state: "Indiana", capital: "Indianapolis", lat: 39.7684, lon: -86.1581 },
    { state: "Ohio", capital: "Columbus", lat: 39.9612, lon: -82.9988 },

    // South Central
    { state: "Kentucky", capital: "Frankfort", lat: 38.2009, lon: -84.8733 },
    { state: "Tennessee", capital: "Nashville", lat: 36.1627, lon: -86.7816 },
    { state: "Mississippi", capital: "Jackson", lat: 32.2988, lon: -90.1848 },
    { state: "Alabama", capital: "Montgomery", lat: 32.3792, lon: -86.3077 },

    // Southeast
    { state: "Georgia", capital: "Atlanta", lat: 33.7490, lon: -84.3880 },
    { state: "Florida", capital: "Tallahassee", lat: 30.4383, lon: -84.2807 },
    { state: "South Carolina", capital: "Columbia", lat: 34.0007, lon: -81.0348 },
    { state: "North Carolina", capital: "Raleigh", lat: 35.7796, lon: -78.6382 },

    // Mid-Atlantic
    { state: "Virginia", capital: "Richmond", lat: 37.5407, lon: -77.4360 },
    { state: "West Virginia", capital: "Charleston", lat: 38.3498, lon: -81.6326 },
    { state: "Maryland", capital: "Annapolis", lat: 38.9784, lon: -76.4922 },
    { state: "Delaware", capital: "Dover", lat: 39.1582, lon: -75.5244 },
    { state: "Pennsylvania", capital: "Harrisburg", lat: 40.2732, lon: -76.8867 },
    { state: "New Jersey", capital: "Trenton", lat: 40.2206, lon: -74.7597 },
    { state: "New York", capital: "Albany", lat: 42.6526, lon: -73.7562 },

    // New England
    { state: "Connecticut", capital: "Hartford", lat: 41.7658, lon: -72.6734 },
    { state: "Rhode Island", capital: "Providence", lat: 41.8240, lon: -71.4128 },
    { state: "Massachusetts", capital: "Boston", lat: 42.3601, lon: -71.0589 },
    { state: "Vermont", capital: "Montpelier", lat: 44.2601, lon: -72.5754 },
    { state: "New Hampshire", capital: "Concord", lat: 43.2081, lon: -71.5376 },
    { state: "Maine", capital: "Augusta", lat: 44.3106, lon: -69.7795 },

    // Non-contiguous
    { state: "Alaska", capital: "Juneau", lat: 58.3019, lon: -134.4197 },
    { state: "Hawaii", capital: "Honolulu", lat: 21.3070, lon: -157.8584 }
];

console.log("// Calculated coordinates using D3.js geoAlbersUsa projection");
console.log("// Viewport: 960x600, Scale: 1070, Translate: [480, 300]");
console.log("// Format: StateCapital(\"State\", \"Capital\", lat, lon, mapX, mapY)\n");

// Group capitals by region for easier reading
const regions = {
    "West Coast": ["Washington", "Oregon", "California"],
    "Mountain West": ["Nevada", "Idaho", "Montana", "Wyoming", "Utah", "Colorado", "Arizona", "New Mexico"],
    "Northern Plains": ["North Dakota", "South Dakota", "Nebraska", "Kansas", "Oklahoma", "Texas"],
    "Upper Midwest": ["Minnesota", "Iowa", "Missouri", "Arkansas", "Louisiana"],
    "Great Lakes": ["Wisconsin", "Illinois", "Michigan", "Indiana", "Ohio"],
    "South Central": ["Kentucky", "Tennessee", "Mississippi", "Alabama"],
    "Southeast": ["Georgia", "Florida", "South Carolina", "North Carolina"],
    "Mid-Atlantic": ["Virginia", "West Virginia", "Maryland", "Delaware", "Pennsylvania", "New Jersey", "New York"],
    "New England": ["Connecticut", "Rhode Island", "Massachusetts", "Vermont", "New Hampshire", "Maine"],
    "Non-contiguous": ["Alaska", "Hawaii"]
};

// Northeast states that use smaller markers
const northeastStates = ["Maryland", "Delaware", "Pennsylvania", "New Jersey", "New York",
                          "Connecticut", "Rhode Island", "Massachusetts", "Vermont", "New Hampshire", "Maine"];

Object.entries(regions).forEach(([region, states]) => {
    console.log(`        // ${region}`);
    states.forEach(stateName => {
        const c = capitals.find(cap => cap.state === stateName);
        if (c) {
            const projected = projection([c.lon, c.lat]);
            if (projected) {
                const [x, y] = projected;
                const mapX = (x / 960).toFixed(3);
                const mapY = (y / 600).toFixed(3);
                const isNortheast = northeastStates.includes(c.state);
                const neFlag = isNortheast ? ", isNortheast = true" : "";
                console.log(`        StateCapital("${c.state}", "${c.capital}", ${c.lat}, ${c.lon}, ${mapX}f, ${mapY}f${neFlag}),`);
            } else {
                console.log(`        // ${c.state} - projection returned null (outside continental US bounds)`);
            }
        }
    });
    console.log("");
});
