import 'dart:io';

import 'package:wearthat/src/logger.dart';

import 'constants.dart';
import 'info.dart';

void skipTileSyncing(Logger log) {
  log.i("Skipping tile syncing...");
  exit(-1);
}

String createTilePreviewResName(TileInformation tile) {
  //File-based resource names must contain only lowercase a-z, 0-9, or underscore
  return "$kAndroidTilePreviewPrefix${tile.name.toLowerCase()}.png";
}

String createTilePreviewResNameForManifest(TileInformation tile) {
  //File-based resource names must contain only lowercase a-z, 0-9, or underscore
  return "$kAndroidTilePreviewPrefix${tile.name.toLowerCase()}";
}
