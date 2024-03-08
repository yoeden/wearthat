import 'package:wearthat/src/logger.dart';

import 'manifest.dart' as manifest;
import 'classes.dart' as classes;
import 'preview.dart' as preview;
import 'tiles.dart' as tiles;

Future addTiles(Logger log) async {
  log.i("Adding tiles...");
  await log.nest((log) async {
    final manifestDoc = await log.nest((log) => manifest.load(log));

    log.i("Loading tiles from pubspec.yaml");
    final loadedTiles = await log.nest((log) => tiles.load(log));

    try {
      for (var tile in loadedTiles) {
        log.d("[+] Adding ${tile.name}");
        await log.nest((log) => preview.add(log, tile));

        await log.nest((log) => classes.addJava(log, tile));

        log.nest((log) => manifest.add(log, manifestDoc, tile));

        log.i("Added ${tile.name}");
      }
    } catch (e) {
      log.e("Failed to add tiles.\nReason: ${e.toString()}\n\nRolling back...");
      clearTiles(log);
    }

    await manifest.save(log, manifestDoc);
  });
}

Future clearTiles(Logger log) async {
  log.i("Clearing tiles...");
  await log.nest((log) async {
    final document = await log.nest((log) => manifest.load(log));

    log.i("Removing tiles from manifest");
    log.nest((log) => manifest.clear(log, document));

    log.i("Removing tiles classes");
    await log.nest((log) => classes.clear(log));

    log.i("Removing tile previews");
    await log.nest((log) => preview.clear(log));

    await manifest.save(log, document);
  });
}
