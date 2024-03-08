import 'package:tiles_gallery/examples/weather/weather.dart';
import 'package:wearthat/tiles.dart';

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

class _Location extends TileWidget {
  final String location;

  _Location({required this.location});

  @override
  TileWidget build() {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        const SizedBox(height: 16, width: 16, child: Image("location")),
        Text(location),
      ],
    );
  }
}

class _Humidity extends TileWidget {
  final int humidity;

  _Humidity({required this.humidity});

  @override
  TileWidget build() {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        const SizedBox(height: 24, width: 24, child: Image("forecast_humidity")),
        Text(humidity.toString()),
      ],
    );
  }
}

class _Forecast extends TileWidget {
  final int temperature;

  _Forecast({required this.temperature});

  @override
  TileWidget build() {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        const SizedBox(height: 64, width: 64, child: Image("forecast_cloudy_day")),
        const SizedBox(width: 8),
        Text("${temperature}", style: TextStyle.display2())
      ],
    );
  }
}
