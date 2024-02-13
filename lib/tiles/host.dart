import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:wear/logger.dart';
import 'package:wear/tiles.dart';
import 'package:wear/tiles/channels/layout.dart';

void runTiles(Tiles host) {
  runZonedGuarded(() async {
    WidgetsFlutterBinding.ensureInitialized();

    final engine = FlutterTileEngine(Map.fromEntries(
      host.getTiles().map((e) => MapEntry(e.name, e)),
    ));
    final channel = WearLayoutChannel(engine: engine);
  }, (error, stackTrace) {
    Logger.red.log(error);
    Logger.red.log(stackTrace);
  });
}

abstract class Tiles {
  const Tiles();

  List<TileService> getTiles();
}

// class Tiles {
//   final List<TileService> tiles;
//   final FlutterTileEngine engine;

//   Tiles({
//     required this.tiles,
//   }) : engine = FlutterTileEngine(Map.fromEntries(
//           tiles.map((e) => MapEntry(e.name, e)),
//         ));

//   Future _tileMethodHandler(MethodCall call) async {
//     try {
//       switch (call.method) {
//         case "requestLayoutForRoute":
//           return _requestTile((call.arguments as List).cast<String>());
//         case "requestResources":
//           return _requestResources(call.arguments as String);
//         case "throwError":
//           throw Error.safeToString(call.arguments);
//         case "destroy":
//           Logger.info("Destroy called");
//           break;
//         default:
//           throw UnimplementedError("Unknown: ${call.method}");
//       }
//     } catch (error) {
//       Logger.red.log(error);
//       Logger.red.log(StackTrace.current);
//       rethrow;
//     }
//   }

//   Future<Map<String, Object>> _requestTile(List<String> args) async {
//     var root = await engine.getTileForServiceAndRoute(args[0], args[1]);
//     var raw = {
//       "tile": root.tile.serialize(),
//       "freshness": root.freshness.toJson(),
//     };
//     return raw;
//   }

//   List<String> _requestResources(String name) {
//     return engine.getResourcesForService(name);
//   }
// }
