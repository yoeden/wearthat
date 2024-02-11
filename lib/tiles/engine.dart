import 'package:wear/widgets.dart';

class FlutterTileEngine {
  final Map<String, TileService> services;

  FlutterTileEngine(this.services);

  Future<RootTile> getTileForServiceAndRoute(String name, String route) async {
    final tileService = _getService(name);

    assert(tileService.routes.containsKey(route));
    final tileBuilder = tileService.routes[route]!;

    final tile = tileBuilder();
    final tileState = await tile.createState();
    return RootTile(
      tile.build(tileState),
      tile.freshness,
    );
  }

  List<String> getResourcesForService(String name) {
    final tileService = _getService(name);

    return tileService.resources;
  }

  TileService _getService(String name) {
    assert(services.containsKey(name));
    final tileService = services[name]!;
    return tileService;
  }
}

class RootTile {
  final TileWidget tile;
  final TileFreshness freshness;

  RootTile(this.tile, this.freshness);

  Map<String, Object> toJson() => {
        "tile": tile.serialize(),
        "freshness": freshness.toJson(),
      };
}
