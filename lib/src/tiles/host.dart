import 'dart:async';

import 'package:flutter/widgets.dart';
import 'package:wear/src/logger.dart';
import 'package:wear/src/tiles/channels/layout.dart';
import 'package:wear/tiles.dart';

void runTiles(AppTiles host) {
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
