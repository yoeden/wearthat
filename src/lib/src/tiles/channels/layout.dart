import 'package:flutter/services.dart';
import 'package:wear/src/logger.dart';
import 'package:wear/tiles.dart';

class WearTilesChannel {
  final MethodChannel _channel = const MethodChannel("wear/tiles");
  final FlutterTileEngine engine;

  WearTilesChannel({
    required this.engine,
  }) {
    _channel.setMethodCallHandler(_callbackHandler);
  }

  Future<dynamic> _callbackHandler(MethodCall call) {
    try {
      switch (call.method) {
        case "requestLayoutForRoute":
          return _requestTile((call.arguments as List).cast<String>());
        case "requestResources":
          return _requestResources(call.arguments as String);
        case "throwError":
          throw Error.safeToString(call.arguments);
        case "destroy":
          Log.i("Destroy called");
          break;
        default:
          throw UnimplementedError("Unknown: ${call.method}");
      }
    } catch (error) {
      Log.e(error);
      Log.e(StackTrace.current);
      rethrow;
    }

    return Future.value(null);
  }

  Future<Map<String, Object>> _requestTile(List<String> args) async {
    var root = await engine.getTileForServiceAndRoute(args[0], args[1]);
    var raw = {
      "tile": root.tile.serialize(),
      "freshness": root.freshness.toJson(),
    };
    return raw;
  }

  Future<List<String>> _requestResources(String name) {
    return Future.value(engine.getResourcesForService(name));
  }
}
