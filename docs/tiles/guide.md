# Tiles

Tiles widgets are designed to feel like the flutter widget tree, but **ITS NOT**.
When designing tiles, make them as simple as possible, don't rely on any services that require `BuildContext`.

Take a look at the [tiles gallery](tiles-gallery.md).

### Adding a tile (build_runner)

1. Create a tile class

```dart
import 'package:wearthat/tiles.dart';

class WeatherTile extends Tile<WeatherState> {
  @override
  Future<WeatherState?> createState() {
    return di.getService<WeatherProvider>().getCurrentWeather();
  }

  @override
  TileWidget build(TileContext context,WeatherState state) {
    return Column(
      children: [
        SizedBox(
          width: 32,
          height: 32,
          child: Image("loading_weather"),
        ),
        Text("I will show my weather here, if i want to...DONT PRESSURE ME !"),
      ],
    );
  }

  @override
  Map<String, TileResourceProvider> resources(TileContext context, WeatherState? state) => {
    'loading_weather': TileResources.asset("assets/weather-tile/loading_weather_icon.png"),
    };
}

```

2. In your main.dart add an entry for the tiles runner and define all the tile services

```dart
// Other imports
import 'package:wearthat/tiles.dart' as wear_tiles; //Importing this and material will cause conflicts

// ...

@pragma('vm:entry-point')
void maintile() {
  wear_tiles.runTiles([
      TileService(
        name: "WeatherTile",
        routes: {
          '/': () => WeatherTile(),
          // If needed more routes
          // '/secondary': () => SecondaryWeatherTile(),
        },
      ),
    ],
  );
}

void main() {
  runApp(const MyApp());
}

// ...
```

3. Define you tile in pubspec.yaml

```yaml
# ...
tiles:
  WeatherTile:
    label: Quick Weather
    preview: assets/weather-tile/preview.png
# ...
```

4. In the root of the project (same level as `lib` folder) run :

```
dart run wear:synctiles 
```

### Adding a tile (Manually)

To add a tile to your wear app, follow this steps :

1. Go to the android folder and add a new class that extends [FlutterTileServices](https://github.com/yoeden/flutter_wear/blob/master/android/src/main/java/yoeden/flutter/wear/tiles/services/FlutterTileService.java).
Using the constructor provide a unique name for your tile (this will later be used in your flutter app).

```java

// ...
//Other imports
import yoeden.flutter.wear.tiles.services.FlutterTileService;
// ...

public class MyCustomTile extends FlutterTileService
{
    public MyCustomTile() {
        super("ThisIsMyTileName");
    }
}
```

2. Add the tile as a service in the manifest : 
`name`: The class of the tile service
`label`: The name of tile that will be displayed to the user
`preview`: The image that will be displayed in the tile menu (when adding your tile).
_The preview image should be a png with transparent background with size of 400x400 px._

```xml
<service
    android:name=".MyCustomTile" 
    android:label="YOUR TILE LABEL HERE"
    android:permission="com.google.android.wearable.permission.BIND_TILE_PROVIDER"
    android:exported="true" >
    <intent-filter>
        <action android:name="androidx.wear.tiles.action.BIND_TILE_PROVIDER" />
    </intent-filter>
    <meta-data
        android:name="androidx.wear.tiles.PREVIEW"
        android:resource="@drawable/PREVIEW_OF_YOUR_TILE" />
</service>
```

3. Repeat the steps 1 & 2 in the `build_runner` way.