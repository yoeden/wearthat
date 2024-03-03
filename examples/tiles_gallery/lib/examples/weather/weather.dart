import 'dart:math' as math;

class FakeWeatherProvider implements WeatherProvider {
  @override
  Future<WeatherInfo> getWeather() => Future.delayed(
        Duration(milliseconds: 2000),
        () => WeatherInfo(
          location: 'Bikini Bottom',
          temperature: math.Random().nextInt(50),
          humidity: math.Random().nextInt(100),
        ),
      );
}

mixin WeatherProvider {
  Future<WeatherInfo> getWeather();
}

class WeatherInfo {
  final String location;
  final int temperature;
  final int humidity;

  WeatherInfo({
    required this.location,
    required this.temperature,
    required this.humidity,
  });
}
