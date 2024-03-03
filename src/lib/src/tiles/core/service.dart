import 'package:wear/tiles.dart';

class TileService {
  final String name;
  final Map<String, TileBuilder> routes;

  //TODO: Add const where ever possible
  const TileService({
    required this.name,
    required this.routes,
  });
}
