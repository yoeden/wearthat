import 'dart:io';

import 'package:wearthat/src/logger.dart';

import 'constants.dart';
import 'info.dart';
import 'utils.dart';

Future<void> add(Logger logger, TileInformation tile) async {
  if (tile.preview == null) {
    logger.w("[-] Tile ${tile.name} has no preview. Skipping...");
    return;
  }

  File preview = File(tile.preview!);
  if (!preview.existsSync()) {
    throw Exception("Preview file not found: ${preview.path}");
  }

  final path = '$kAndroidDrawablesFolder${createTilePreviewResName(tile)}';

  logger.d("[+] Copying tile preview to $path");
  await File(path).create(recursive: true);
  await preview.copy(path);
}

Future<void> clear(Logger log) async {
  final drawables = Directory(kAndroidDrawablesFolder);
  if (!drawables.existsSync() || drawables.listSync().isEmpty) {
    log.d("[*] No tiles classes to remove");
    return;
  }

  for (var drawable in drawables.listSync()) {
    if (drawable.uri.pathSegments.last.startsWith(kAndroidTilePreviewPrefix)) {
      log.d("[-] Deleting $drawable");
      await drawable.delete();
    }
  }
  log.d("[-] Done");
}
