import 'dart:io';

import 'package:flutterwear/src/logger.dart';
import 'package:flutterwear/src/tiles/syncing/info.dart';
import 'package:flutterwear/src/tiles/syncing/utils.dart';
import 'package:yaml/yaml.dart';

Future<List<TileInformation>> load(Logger log) async {
  final pubspec = loadYaml(await File('pubspec.yaml').readAsString());
  final pubspecTiles = pubspec['tiles'];
  if (pubspecTiles == null) {
    log.e("Tiles not found in pubspec.yaml");
    skipTileSyncing(log);
  }
  final tiles = (pubspecTiles as YamlMap).entries.map((e) {
    return TileInformation(
      name: e.key,
      label: e.value['label'],
      preview: e.value['preview'],
    );
  }).toList();

  return tiles;
}
