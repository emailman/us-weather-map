"""
D3.js-compatible Albers USA projection calculator for US state capitals.
Implements the same projection as D3's geoAlbersUsa() for 960x600 viewport.

Run: python calculate-coordinates.py
"""

import math

# Projection parameters matching D3's geoAlbersUsa
# https://github.com/d3/d3-geo/blob/main/src/projection/albersUsa.js

def albers_projection(phi0, phi1, phi2, lambda0):
    """
    Creates an Albers Equal Area Conic projection.
    phi0, phi1, phi2 are parallels in radians
    lambda0 is the central meridian in radians
    """
    n = 0.5 * (math.sin(phi1) + math.sin(phi2))
    C = math.cos(phi1) ** 2 + 2 * n * math.sin(phi1)
    rho0 = math.sqrt(C - 2 * n * math.sin(phi0)) / n

    def project(lon, lat):
        lambda_ = math.radians(lon)
        phi = math.radians(lat)
        rho = math.sqrt(C - 2 * n * math.sin(phi)) / n
        theta = n * (lambda_ - lambda0)
        x = rho * math.sin(theta)
        y = rho0 - rho * math.cos(theta)
        return (x, y)

    return project

# D3 geoAlbersUsa composite projection
# Lower 48: Albers with parallels at 29.5° and 45.5°, centered at 96°W, 38°N
lower48 = albers_projection(
    phi0=math.radians(38),
    phi1=math.radians(29.5),
    phi2=math.radians(45.5),
    lambda0=math.radians(-96)
)

# Alaska: Albers with parallels at 55° and 65°, centered at 154°W
alaska_albers = albers_projection(
    phi0=math.radians(60),
    phi1=math.radians(55),
    phi2=math.radians(65),
    lambda0=math.radians(-154)
)

# Hawaii: Albers with parallels at 8° and 18°, centered at 157°W
hawaii_albers = albers_projection(
    phi0=math.radians(13),
    phi1=math.radians(8),
    phi2=math.radians(18),
    lambda0=math.radians(-157)
)

def project_albers_usa(lon, lat, scale=1070, translate=(480, 300)):
    """
    Project geographic coordinates using D3-compatible Albers USA projection.
    Returns (x, y) pixel coordinates for a 960x600 viewport.

    For Alaska and Hawaii, we return special values that need manual adjustment
    based on the actual SVG inset positions.
    """
    # Alaska bounding box - return None to signal manual handling needed
    # Juneau is at lon=-134.4, lat=58.3, so we need lon < -130 to catch it
    if lon < -130 and lat > 50:
        return None, "ALASKA"

    # Hawaii bounding box - return None to signal manual handling needed
    if lon < -150 and lat < 30:
        return None, "HAWAII"

    # Continental US
    x, y = lower48(lon, lat)
    x = x * scale + translate[0]
    y = -y * scale + translate[1]
    return (x, y), "CONTINENTAL"


# All 50 US state capitals with geographic coordinates
capitals = [
    # West Coast
    ("Washington", "Olympia", 47.0379, -122.9007),
    ("Oregon", "Salem", 44.9429, -123.0351),
    ("California", "Sacramento", 38.5816, -121.4944),

    # Mountain West
    ("Nevada", "Carson City", 39.1638, -119.7674),
    ("Idaho", "Boise", 43.6150, -116.2023),
    ("Montana", "Helena", 46.5891, -112.0391),
    ("Wyoming", "Cheyenne", 41.1400, -104.8202),
    ("Utah", "Salt Lake City", 40.7608, -111.8910),
    ("Colorado", "Denver", 39.7392, -104.9903),
    ("Arizona", "Phoenix", 33.4484, -112.0740),
    ("New Mexico", "Santa Fe", 35.6870, -105.9378),

    # Northern Plains
    ("North Dakota", "Bismarck", 46.8083, -100.7837),
    ("South Dakota", "Pierre", 44.3683, -100.3510),
    ("Nebraska", "Lincoln", 40.8258, -96.6852),
    ("Kansas", "Topeka", 39.0473, -95.6752),
    ("Oklahoma", "Oklahoma City", 35.4676, -97.5164),
    ("Texas", "Austin", 30.2672, -97.7431),

    # Upper Midwest
    ("Minnesota", "Saint Paul", 44.9537, -93.0900),
    ("Iowa", "Des Moines", 41.5868, -93.6250),
    ("Missouri", "Jefferson City", 38.5767, -92.1735),
    ("Arkansas", "Little Rock", 34.7465, -92.2896),
    ("Louisiana", "Baton Rouge", 30.4515, -91.1871),

    # Great Lakes
    ("Wisconsin", "Madison", 43.0731, -89.4012),
    ("Illinois", "Springfield", 39.7817, -89.6501),
    ("Michigan", "Lansing", 42.7325, -84.5555),
    ("Indiana", "Indianapolis", 39.7684, -86.1581),
    ("Ohio", "Columbus", 39.9612, -82.9988),

    # South Central
    ("Kentucky", "Frankfort", 38.2009, -84.8733),
    ("Tennessee", "Nashville", 36.1627, -86.7816),
    ("Mississippi", "Jackson", 32.2988, -90.1848),
    ("Alabama", "Montgomery", 32.3792, -86.3077),

    # Southeast
    ("Georgia", "Atlanta", 33.7490, -84.3880),
    ("Florida", "Tallahassee", 30.4383, -84.2807),
    ("South Carolina", "Columbia", 34.0007, -81.0348),
    ("North Carolina", "Raleigh", 35.7796, -78.6382),

    # Mid-Atlantic
    ("Virginia", "Richmond", 37.5407, -77.4360),
    ("West Virginia", "Charleston", 38.3498, -81.6326),
    ("Maryland", "Annapolis", 38.9784, -76.4922),
    ("Delaware", "Dover", 39.1582, -75.5244),
    ("Pennsylvania", "Harrisburg", 40.2732, -76.8867),
    ("New Jersey", "Trenton", 40.2206, -74.7597),
    ("New York", "Albany", 42.6526, -73.7562),

    # New England
    ("Connecticut", "Hartford", 41.7658, -72.6734),
    ("Rhode Island", "Providence", 41.8240, -71.4128),
    ("Massachusetts", "Boston", 42.3601, -71.0589),
    ("Vermont", "Montpelier", 44.2601, -72.5754),
    ("New Hampshire", "Concord", 43.2081, -71.5376),
    ("Maine", "Augusta", 44.3106, -69.7795),

    # Non-contiguous
    ("Alaska", "Juneau", 58.3019, -134.4197),
    ("Hawaii", "Honolulu", 21.3070, -157.8584),
]

# Northeast states that use smaller markers
northeast_states = {
    "Maryland", "Delaware", "Pennsylvania", "New Jersey", "New York",
    "Connecticut", "Rhode Island", "Massachusetts", "Vermont", "New Hampshire", "Maine"
}

# Define regions for organized output
regions = {
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
}

# Create lookup by state name
capitals_dict = {c[0]: c for c in capitals}

print("// Calculated coordinates using Albers USA projection")
print("// Viewport: 960x600, Scale: 1070, Translate: [480, 300]")
print("// Format: StateCapital(\"State\", \"Capital\", lat, lon, mapX, mapY)")
print()

for region, states in regions.items():
    print(f"        // {region}")
    for state_name in states:
        if state_name in capitals_dict:
            state, capital, lat, lon = capitals_dict[state_name]
            result, region_type = project_albers_usa(lon, lat)
            is_northeast = state in northeast_states
            ne_flag = ", isNortheast = true" if is_northeast else ""

            if region_type == "ALASKA":
                # Alaska inset: Based on SVG, Alaska inset is at approximately x=20-250, y=450-580
                # Juneau is in southeastern Alaska
                # SVG analysis: Alaska path starts at M161.1,453.7
                # Juneau should be roughly at x~170, y~500 -> mapX~0.177, mapY~0.833
                print(f'        StateCapital("{state}", "{capital}", {lat}, {lon}, 0.177f, 0.833f),')
            elif region_type == "HAWAII":
                # Hawaii inset: Based on SVG, Hawaii inset is at approximately x=230-360, y=520-580
                # Honolulu is on Oahu, in the middle of the chain
                # SVG analysis: Main Hawaii islands around x=280-340, y=530-570
                # Honolulu should be roughly at x~310, y~545 -> mapX~0.323, mapY~0.908
                print(f'        StateCapital("{state}", "{capital}", {lat}, {lon}, 0.323f, 0.908f),')
            else:
                x, y = result
                map_x = x / 960
                map_y = y / 600
                print(f'        StateCapital("{state}", "{capital}", {lat}, {lon}, {map_x:.3f}f, {map_y:.3f}f{ne_flag}),')
    print()
