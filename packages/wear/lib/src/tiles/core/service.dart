import 'package:flutterwear/tiles.dart';

/// Represents a tile service.
///
/// A [TileService] is the parent of tiles and contains all the possible routes.
class TileService {
  final String name;
  final Map<String, TileBuilder> routes;

  //TODO: Add const where ever possible
  const TileService({
    required this.name,
    required this.routes,
  });
}
