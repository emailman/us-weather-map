"""
Builds the coordinate picker HTML tool from us_map.xml.
Shows progress every few seconds.
"""
import re
import sys

print("=== Building Coordinate Picker Tool ===")
print("Progress: Starting...")
sys.stdout.flush()

# Read the XML file
xml_path = "../composeApp/src/commonMain/composeResources/drawable/us_map.xml"
print(f"Progress: Reading {xml_path}")
sys.stdout.flush()

with open(xml_path, 'r', encoding='utf-8') as f:
    content = f.read()

print("Progress: XML file loaded successfully")
sys.stdout.flush()

# Extract state paths using regex
# Pattern: <!-- StateName --> followed by <path ... android:pathData="..."/>
state_pattern = r'<!-- ([A-Za-z ]+(?:\([^)]+\))?) -->\s*<path[^>]*android:pathData="([^"]+)"'
matches = re.findall(state_pattern, content, re.DOTALL)

print(f"Progress: Found {len(matches)} state paths")
sys.stdout.flush()

# Current capital coordinates from StateCapital.kt
capitals = {
    "Washington": ("Olympia", 0.146, 0.137),
    "Oregon": ("Salem", 0.133, 0.198),
    "California": ("Sacramento", 0.121, 0.400),
    "Nevada": ("Carson City", 0.149, 0.393),
    "Idaho": ("Boise", 0.219, 0.276),
    "Montana": ("Helena", 0.286, 0.202),
    "Wyoming": ("Cheyenne", 0.372, 0.392),
    "Utah": ("Salt Lake City", 0.269, 0.382),
    "Colorado": ("Denver", 0.367, 0.435),
    "Arizona": ("Phoenix", 0.242, 0.608),
    "New Mexico": ("Santa Fe", 0.345, 0.560),
    "North Dakota": ("Bismarck", 0.436, 0.222),
    "South Dakota": ("Pierre", 0.440, 0.298),
    "Nebraska": ("Lincoln", 0.490, 0.411),
    "Kansas": ("Topeka", 0.505, 0.467),
    "Oklahoma": ("Oklahoma City", 0.476, 0.579),
    "Texas": ("Austin", 0.469, 0.789),
    "Minnesota": ("Saint Paul", 0.540, 0.281),
    "Iowa": ("Des Moines", 0.534, 0.387),
    "Missouri": ("Jefferson City", 0.558, 0.480),
    "Arkansas": ("Little Rock", 0.559, 0.600),
    "Louisiana": ("Baton Rouge", 0.581, 0.733),
    "Wisconsin": ("Madison", 0.593, 0.336),
    "Illinois": ("Springfield", 0.594, 0.439),
    "Michigan": ("Lansing", 0.662, 0.336),
    "Indiana": ("Indianapolis", 0.646, 0.432),
    "Ohio": ("Columbus", 0.691, 0.417),
    "Kentucky": ("Frankfort", 0.668, 0.478),
    "Tennessee": ("Nashville", 0.643, 0.547),
    "Mississippi": ("Jackson", 0.595, 0.674),
    "Alabama": ("Montgomery", 0.658, 0.663),
    "Georgia": ("Atlanta", 0.686, 0.615),
    "Florida": ("Tallahassee", 0.696, 0.718),
    "South Carolina": ("Columbia", 0.739, 0.595),
    "North Carolina": ("Raleigh", 0.770, 0.530),
    "Virginia": ("Richmond", 0.799, 0.458),
    "West Virginia": ("Charleston", 0.732, 0.452),
    "Maryland": ("Annapolis", 0.829, 0.437),
    "Delaware": ("Dover", 0.847, 0.430),
    "Pennsylvania": ("Harrisburg", 0.796, 0.362),
    "New Jersey": ("Trenton", 0.851, 0.388),
    "New York": ("Albany", 0.855, 0.285),
    "Connecticut": ("Hartford", 0.882, 0.310),
    "Rhode Island": ("Providence", 0.905, 0.305),
    "Massachusetts": ("Boston", 0.907, 0.278),
    "Vermont": ("Montpelier", 0.866, 0.218),
    "New Hampshire": ("Concord", 0.883, 0.248),
    "Maine": ("Augusta", 0.913, 0.192),
    "Alaska": ("Juneau", 0.177, 0.833),
    "Hawaii": ("Honolulu", 0.323, 0.908),
}

print("Progress: Building HTML template...")
sys.stdout.flush()

# Build HTML
html = '''<!DOCTYPE html>
<html>
<head>
    <title>US Capital Coordinate Picker</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: #f5f5f5;
        }
        h1 { margin-top: 0; }
        .container { display: flex; gap: 20px; }
        #map-container {
            position: relative;
            border: 2px solid #333;
            background: white;
            flex-shrink: 0;
        }
        svg { display: block; cursor: crosshair; }
        .state { fill: #BBDEFB; stroke: #1976D2; stroke-width: 1; }
        .state:hover { fill: #90CAF9; }
        .marker {
            pointer-events: none;
        }
        .marker-dot {
            fill: #FF5722;
            stroke: white;
            stroke-width: 2;
        }
        .marker-label {
            font-size: 8px;
            fill: #333;
            text-anchor: middle;
        }
        #sidebar {
            width: 400px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        #state-list {
            max-height: 300px;
            overflow-y: auto;
            border: 1px solid #ccc;
            background: white;
            padding: 10px;
        }
        .state-item {
            padding: 5px 10px;
            cursor: pointer;
            border-radius: 4px;
            margin: 2px 0;
        }
        .state-item:hover { background: #e3f2fd; }
        .state-item.selected { background: #1976D2; color: white; }
        .state-item.done { background: #c8e6c9; }
        #coords {
            padding: 15px;
            background: white;
            border: 1px solid #ccc;
            font-family: monospace;
            font-size: 12px;
        }
        #output {
            flex: 1;
            min-height: 200px;
            padding: 10px;
            background: #263238;
            color: #aed581;
            font-family: monospace;
            font-size: 11px;
            overflow: auto;
            white-space: pre;
            border: 1px solid #ccc;
        }
        button {
            padding: 10px 20px;
            background: #1976D2;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            font-size: 14px;
        }
        button:hover { background: #1565C0; }
        .instructions {
            background: #fff3e0;
            padding: 10px;
            border-radius: 4px;
            font-size: 13px;
        }
    </style>
</head>
<body>
    <h1>US State Capital Coordinate Picker</h1>
    <div class="instructions">
        <strong>Instructions:</strong>
        1. Select a state from the list on the right.
        2. Click on the map where the capital should be.
        3. The coordinates will be recorded. States turn green when set.
        4. Click "Copy All Coordinates" when done.
    </div>
    <br>
    <div class="container">
        <div id="map-container">
            <svg width="960" height="600" viewBox="0 0 960 600" id="map">
                <!-- Background -->
                <rect width="960" height="600" fill="#E3F2FD"/>
'''

print("Progress: Adding state paths to HTML...")
sys.stdout.flush()

# Add state paths
state_count = 0
for name, path_data in matches:
    if name == "Background":
        continue
    clean_name = name.replace(" (inset)", "")
    html += f'                <path class="state" id="state-{clean_name.replace(" ", "-")}" d="{path_data}"><title>{clean_name}</title></path>\n'
    state_count += 1
    if state_count % 10 == 0:
        print(f"Progress: Added {state_count} state paths...")
        sys.stdout.flush()

print(f"Progress: Added all {state_count} state paths")
sys.stdout.flush()

# Add markers group
html += '''
                <!-- Capital markers -->
                <g id="markers">
'''

print("Progress: Adding capital markers...")
sys.stdout.flush()

marker_count = 0
for state, (city, mx, my) in capitals.items():
    x = mx * 960
    y = my * 600
    html += f'''                    <g class="marker" id="marker-{state.replace(" ", "-")}" data-state="{state}">
                        <circle class="marker-dot" cx="{x:.1f}" cy="{y:.1f}" r="6"/>
                        <text class="marker-label" x="{x:.1f}" y="{y - 10:.1f}">{city}</text>
                    </g>
'''
    marker_count += 1
    if marker_count % 10 == 0:
        print(f"Progress: Added {marker_count} markers...")
        sys.stdout.flush()

print(f"Progress: Added all {marker_count} markers")
sys.stdout.flush()

html += '''                </g>
            </svg>
        </div>
        <div id="sidebar">
            <div id="state-list">
'''

# Add state list
for state in sorted(capitals.keys()):
    city = capitals[state][0]
    html += f'                <div class="state-item" data-state="{state}">{state} - {city}</div>\n'

html += '''            </div>
            <div id="coords">
                <strong>Current Selection:</strong><br>
                State: <span id="sel-state">None</span><br>
                Capital: <span id="sel-city">-</span><br>
                <br>
                <strong>Click Position:</strong><br>
                mapX: <span id="sel-x">-</span><br>
                mapY: <span id="sel-y">-</span>
            </div>
            <button onclick="copyOutput()">Copy All Coordinates</button>
            <div id="output">// Click states on the map to set coordinates\n// Green = coordinate set</div>
        </div>
    </div>

    <script>
        const capitals = ''' + str({k: list(v) for k, v in capitals.items()}).replace("'", '"') + ''';

        const updatedCoords = {};
        let selectedState = null;

        // State list click handler
        document.querySelectorAll('.state-item').forEach(item => {
            item.addEventListener('click', function() {
                document.querySelectorAll('.state-item').forEach(i => i.classList.remove('selected'));
                this.classList.add('selected');
                selectedState = this.dataset.state;
                document.getElementById('sel-state').textContent = selectedState;
                document.getElementById('sel-city').textContent = capitals[selectedState][0];
            });
        });

        // Map click handler
        document.getElementById('map').addEventListener('click', function(e) {
            if (!selectedState) {
                alert('Please select a state from the list first!');
                return;
            }

            const rect = this.getBoundingClientRect();
            const scaleX = 960 / rect.width;
            const scaleY = 600 / rect.height;

            const x = (e.clientX - rect.left) * scaleX;
            const y = (e.clientY - rect.top) * scaleY;

            const normX = x / 960;
            const normY = y / 600;

            // Update display
            document.getElementById('sel-x').textContent = normX.toFixed(3);
            document.getElementById('sel-y').textContent = normY.toFixed(3);

            // Store the update
            updatedCoords[selectedState] = [normX, normY];

            // Update marker position
            const marker = document.getElementById('marker-' + selectedState.replace(/ /g, '-'));
            if (marker) {
                const circle = marker.querySelector('circle');
                const text = marker.querySelector('text');
                circle.setAttribute('cx', x);
                circle.setAttribute('cy', y);
                text.setAttribute('x', x);
                text.setAttribute('y', y - 10);
            }

            // Mark state as done in list
            document.querySelector(`.state-item[data-state="${selectedState}"]`).classList.add('done');

            // Update output
            updateOutput();
        });

        function updateOutput() {
            let output = '// Updated coordinates - copy to StateCapital.kt\\n\\n';

            const allStates = Object.keys(capitals).sort();
            for (const state of allStates) {
                const city = capitals[state][0];
                let mx, my;
                if (updatedCoords[state]) {
                    mx = updatedCoords[state][0].toFixed(3);
                    my = updatedCoords[state][1].toFixed(3);
                } else {
                    mx = capitals[state][1].toFixed(3);
                    my = capitals[state][2].toFixed(3);
                }
                const isNE = ["Maryland", "Delaware", "Pennsylvania", "New Jersey", "New York",
                              "Connecticut", "Rhode Island", "Massachusetts", "Vermont",
                              "New Hampshire", "Maine"].includes(state);
                const neFlag = isNE ? ", isNortheast = true" : "";
                output += `StateCapital("${state}", "${city}", lat, lon, ${mx}f, ${my}f${neFlag}),\\n`;
            }

            document.getElementById('output').textContent = output;
        }

        function copyOutput() {
            const output = document.getElementById('output').textContent;
            navigator.clipboard.writeText(output).then(() => {
                alert('Coordinates copied to clipboard!');
            });
        }

        // Initial output
        updateOutput();
    </script>
</body>
</html>
'''

print("Progress: Writing HTML file...")
sys.stdout.flush()

output_path = "capital-picker.html"
with open(output_path, 'w', encoding='utf-8') as f:
    f.write(html)

print(f"\n=== COMPLETE ===")
print(f"Created: tools/{output_path}")
print(f"Total states: {state_count}")
print(f"Total markers: {marker_count}")
print(f"\nTo use: Open tools/capital-picker.html in your browser")
