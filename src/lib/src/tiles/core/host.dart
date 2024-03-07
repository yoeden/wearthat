import 'dart:async';

import 'package:flutter/widgets.dart';
import 'package:wear/src/logger.dart';
import 'package:wear/src/tiles/channels/layout.dart';
import 'package:wear/tiles.dart';

/// Accessing the plugin subsystem and giving tile information.
///
/// Calling [runTiles] again cause unexpected behavior.
void runTiles(List<TileService> tiles) {
  runZonedGuarded(() async {
    // As needed to provide access to the plugin subsystem.
    WidgetsFlutterBinding.ensureInitialized();

    // Create the tile engine to discover all tiles layout and resources.
    final engine = FlutterTileEngine(
      Map.fromEntries(tiles.map((e) => MapEntry(e.name, e))),
    );

    // Create the channel to request tile information (will live as long as the tile service request is running).
    // ignore: unused_local_variable
    final channel = WearTilesChannel(engine: engine);
  }, (error, stackTrace) {
    Log.e(error);
    Log.e(stackTrace);
  });
}
