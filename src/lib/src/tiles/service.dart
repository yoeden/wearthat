import 'package:wear/tiles.dart';

class TileService {
  final String name;
  final List<String> resources;
  final String? initialRoute;
  final Map<String, TileBuilder> routes;

  TileService({
    required this.name,
    required this.routes,
    this.initialRoute = "/",
    this.resources = const [],
  });
}
