import 'dart:io';

import 'package:wearthat/src/logger.dart';
import 'package:yaml/yaml.dart';

import 'info.dart';
import 'utils.dart';

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
