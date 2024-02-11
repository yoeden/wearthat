class RouteManager {
  factory RouteManager.fromRoutes(List<String> routes) {
    return RouteManager(
      routes.map((e) => RoutePath(e, Uri.parse(e).pathSegments)).toList(),
    );
  }

  final List<RoutePath> paths;

  RouteManager(this.paths);

  RouteData? getRoute(List<String> routes, String route) {
    for (final path in routes) {
      final routePath = RoutePath(path, Uri.parse(path).pathSegments);
      final data = routePath.tryGet(route);
      if (data != null) return data;
    }

    return null;
  }
}

class RoutePath {
  final String template;
  final List<String> segments;

  RoutePath(this.template, this.segments);

  bool isMatching(String route) {
    final givenSegments = Uri.parse(route).pathSegments;

    if (segments.length != givenSegments.length) return false;

    for (int i = 0; i < givenSegments.length; i++) {
      if (segments[i].startsWith(":")) continue;
      if (segments[i] != givenSegments[i]) return false;
    }

    return true;
  }

  RouteData? tryGet(String route) {
    final givenSegments = Uri.parse(route).pathSegments;
    if (segments.length != givenSegments.length) return null;

    final Map<String, dynamic> values = {};

    for (int i = 0; i < givenSegments.length; i++) {
      if (segments[i].startsWith(":")) {
        values[segments[i].substring(1)] = _parseValue(givenSegments[i]);
      } else if (segments[i] != givenSegments[i]) {
        return null;
      }
    }

    return RouteData(template, route, values);
  }

  dynamic _parseValue(String value) {
    final d = double.tryParse(value);
    if (d != null) return d;

    final i = int.tryParse(value);
    if (i != null) return i;

    return value;
  }
}

class RouteData {
  final String template;
  final String path;
  final Map<String, dynamic> parameters;

  RouteData(this.template, this.path, this.parameters);

  @override
  String toString() {
    return "Path: $path, Params: ${parameters}";
  }
}
