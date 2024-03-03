import 'dart:async';

import 'package:flutter/widgets.dart';
import 'package:wear/src/logger.dart';
import 'package:wear/src/tiles/channels/layout.dart';
import 'package:wear/tiles.dart';

void runTiles(List<TileService> tiles) {
  runZonedGuarded(() async {
    WidgetsFlutterBinding.ensureInitialized();

    final engine = FlutterTileEngine(
      Map.fromEntries(
        tiles.map((e) => MapEntry(e.name, e)),
      ),
    );
    final channel = WearTilesChannel(engine: engine);
  }, (error, stackTrace) {
    Log.e(error);
    Log.e(stackTrace);
  });
}
