# Flutter Wear ⌚

Flutter wear is a library for flutter aspiring to be a one-stop shop for all related features for android wear (We're not there yet, but will slowly get there 🙃).

__This library is still in very very early development, and breaking changes may be introduced every version !__

<a href="https://www.buymeacoffee.com/yoeden" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>

## Planned features 📋

 - [ ] Add support for tile lifecycle events (onTileAddEvent() for example)
 - [ ] Support tile animations
 - [ ] Support hot reload for tiles (only in preview).
 - [ ] Incorporate app themes
 - [ ] Improve UI exceptions
 - [ ] Create wear widgets system (like `package:flutter/material.dart`)
 - [x] Upgrade tiles to new version (1.2.0)
 - [ ] Add Tests
 - [X] Automate tiles syncing
 - [ ] Add support for health services for wear apps and tiles (`androidx.wear.protolayout.expression.PlatformHealthSources` for tiles, research more for in app service)

## Installing

1. With Dart:
```$ dart pub add flutter_wear```

2. With Flutter:
```$ flutter pub add flutter_wear```

3. Manually:
```
dependencies:
  benchmark: ^0.3.0
```

__Don't forget to set android compileSdkVersion to 34 and minSdkVersion to 28 in the build.gradle !__
```gradle
android {
  compileSdkVersion 34
  ...
  defaultConfig {
    minSdkVersion 28
  }
}
```

## Features ✨

### Communication between devices 📞

Get all available nodes :
```dart
final nodes = Wear.getNodes();
```

Send message to device :
```dart
// Path is like route, to know where to direct to message
Wear.send(WearMessage.string("PATH", data), nodeId: watch.id /* or phone */);
```

Send message to all devices (Fit for cases where only one device is connected) :
```dart
Wear.send(WearMessage.string("PATH", data));
```

Listen for incoming messages :
```dart
Wear.listen("PATH", _onIncomingMessage);

// Dont forget to removeListener !
Wear.removeListener("PATH", _onIncomingMessage);
```

![](/docs/media/communication.gif)

### Tiles 🍫

Tiles can finally be made using Flutter 💙 !

The following code : 

```dart
class WeatherTile extends Tile<WeatherInfo> {
  const WeatherTile();

  @override
  Future<WeatherInfo?> createState() {
    return FakeWeatherProvider().getWeather();
  }

  @override
  TileWidget build(TileContext context, WeatherInfo? state) {
    if (state == null) return const Text("Something went wrong...");

    return PrimaryLayout(
      body: _Forecast(temperature: state.temperature),
      title: _Location(location: state.location),
      chip: _Humidity(humidity: state.humidity),
    );
  }

  @override
  Map<String, TileResourceProvider> resources(TileContext context, WeatherInfo? state) => {
        'forecast_cloudy_day': TileResources.asset("assets/weather/forecast_cloudy_day.png"),
        'forecast_humidity': TileResources.asset("assets/weather/forecast_humidity.png"),
        'location': TileResources.asset("assets/weather/location.png"),
      };
}
```

![My image](/docs/media/tile_proof.gif)

##### Notice of the tile is on the watch tiles carousal between other tiles (Tile Magic 😵).

[Learn more about tiles here !](docs/tiles/guide.md)

## Reading Materials 👓
 - https://developer.android.com/design/ui/wear/guides/surfaces/tiles
 - https://developer.android.com/training/wearables/tiles
 - https://developer.android.com/design/ui/wear/guides/foundations/getting-started
 - [The docs, duh](/docs/docs.md)