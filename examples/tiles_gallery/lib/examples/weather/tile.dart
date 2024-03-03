import 'package:tiles_gallery/examples/weather/weather.dart';
import 'package:wear/tiles.dart';

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
      body: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          const SizedBox(height: 64, width: 64, child: Image("assets/weather/forecast_cloudy_day.png")),
          const SizedBox(width: 8),
          Text("${state.temperature}", style: TextStyle.display2())
        ],
      ),
      title: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          const SizedBox(height: 16, width: 16, child: Image("assets/weather/location.png")),
          Text(state.location),
        ],
      ),
      chip: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          const SizedBox(height: 24, width: 24, child: Image("assets/weather/forecast_humidity.png")),
          Text(state.humidity.toString()),
        ],
      ),
    );
  }

  @override
  Map<String, TileResourceProvider> resources(TileContext context, WeatherInfo? state) => {
        'forecast_cloudy_day': TileResources.asset("assets/weather/forecast_cloudy_day.png"),
        'location': TileResources.asset("assets/weather/location.png"),
        'forecast_humidity': TileResources.asset("assets/weather/forecast_humidity.png"),
      };
}
