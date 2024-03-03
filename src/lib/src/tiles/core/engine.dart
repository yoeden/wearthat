import 'dart:convert';

import 'package:wear/src/tiles/core/resource.dart';
import 'package:wear/tiles.dart';

class FlutterTileEngine {
  final Map<String, TileService> services;

  FlutterTileEngine(this.services);

  Future<RootTile> getTileForServiceAndRoute(String name, String route, String rawState) async {
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

  Future<List<TileResource>> getResourcesForService(String name) async {
    final tileService = _getService(name);
    final resources = <TileResource>[];

    for (final tile in tileService.routes.values) {
      final tileRoute = tile();
      final state = await tileRoute.createState();
      final routeResources = tileRoute.resources(/*TODO:*/ DemoContext(), state);

      for (final res in routeResources.entries) {
        resources.add(TileResource(res.key, await res.value.resolve()));
      }
    }

    return resources;
  }

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
