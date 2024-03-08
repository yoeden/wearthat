import 'dart:io';

import 'package:wearthat/src/logger.dart';

import 'constants.dart';
import 'info.dart';

Future<void> clear(Logger log) async {
  final tiles = Directory(kAndroidTilesFolder);
  if (!tiles.existsSync() || tiles.listSync().isEmpty) {
    log.d("[*] No tiles classes to remove");
    return;
  }

  log.d("[-] Deleting ${tiles.listSync().length} tiles classes");
  await tiles.delete(recursive: true);
  log.d("[-] Done");
}

Future<void> addJava(Logger log, TileInformation tile) async {
  log.d(
      "[+] Writing tile class ${tile.name} to /$kAndroidTilesPackage/${tile.name}.java");

  final code = kAndroidJavaTileClassTemplate.replaceAll(":name:", tile.name);

  final tileFile = File(("$kAndroidTilesFolder${tile.name}.java"));
  await tileFile.create(recursive: true);
  await tileFile.writeAsString(code);

  log.d("[+] Written tile class ${tile.name}");
}
