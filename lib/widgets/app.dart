import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:wear/logger.dart';
import 'package:wear/widgets.dart';

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

class TileHost {
  final List<TileService> tiles;
  final FlutterTileEngine _engine;

  TileHost({
    required this.tiles,
  }) : _engine = FlutterTileEngine(Map.fromEntries(tiles.map((e) => MapEntry(e.name, e))));

  Future run() async {
    runZonedGuarded(() async {
      WidgetsFlutterBinding.ensureInitialized();
      const MethodChannel channel = MethodChannel("flutter_tile");
      channel.setMethodCallHandler(_tileMethodHandler);
    }, (error, stackTrace) {
      Logger.red.log(error);
      Logger.red.log(stackTrace);
    });
  }

  Future _tileMethodHandler(MethodCall call) async {
    try {
      switch (call.method) {
        case "requestLayoutForRoute":
          return await _requestTile((call.arguments as List).cast<String>());
        case "requestResources":
          return _requestResources(call.arguments as String);
        case "throwError":
          throw Error.safeToString(call.arguments);
        default:
          throw UnimplementedError("Unknow: ${call.method}");
      }
    } catch (error) {
      Logger.red.log(error);
      Logger.red.log(StackTrace.current);
      rethrow;
    }
  }

  Future<Map<String, Object>> _requestTile(List<String> args) async {
    var root = await _engine.getTileForServiceAndRoute(args[0], args[1]);
    var raw = {
      "tile": root.tile.serialize(),
      "freshness": root.freshness.toJson(),
    };
    return raw;
  }

  List<String> _requestResources(String name) {
    return _engine.getResourcesForService(name);
  }
}
