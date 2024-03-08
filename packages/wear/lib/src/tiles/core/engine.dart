import 'dart:convert';

import 'package:wearthat/tiles.dart';

/// Represents a tile engine for Flutter.
class FlutterTileEngine {
  final Map<String, TileService> services;

  FlutterTileEngine(this.services);

  /// Retrieves the root tile for a specified service and route.
  Future<RootTile> getTileForServiceAndRoute(
      String name, String route, String rawState) async {
    final tileService = _getService(name);

    assert(tileService.routes.containsKey(route), "Route $route not found");
    final tileBuilder = tileService.routes[route]!;

    final tile = tileBuilder();

    final state = await _initState(tile, rawState);

    return RootTile(
      tile.build(/*TODO:*/ DemoContext(), state),
      tile.freshness,
    );
  }

  /// Retrieves resources for a specified service.
  Future<List<TileResource>> getResourcesForService(String name) async {
    final tileService = _getService(name);
    final resources = <TileResource>[];

    for (final tile in tileService.routes.values) {
      final tileRoute = tile();
      final state = await tileRoute.createState();
      final routeResources =
          tileRoute.resources(/*TODO:*/ DemoContext(), state);

      for (final res in routeResources.entries) {
        resources.add(TileResource(res.key, await res.value.resolve()));
      }
    }

    return resources;
  }

  /// Initializes the state for a tile.
  Future<dynamic> _initState(Tile<dynamic> tile, String rawState) async {
    dynamic state;
    if (rawState.isNotEmpty) {
      assert(tile.fromJson != null);
      state = tile.fromJson!(jsonDecode(rawState));
    } else {
      state = await tile.createState();
    }
    return state;
  }

  /// Retrieves a tile service by name.
  TileService _getService(String name) {
    assert(services.containsKey(name), "Service $name not found");
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
