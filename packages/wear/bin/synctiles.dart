import 'dart:async';
import 'package:wearthat/src/logger.dart';
import 'package:wearthat/src/tiles/syncing/sync.dart';

// Run the command: `dart run wear:synctiles -v` from the pubspec.yaml directory of the project
Future<void> main(List<String> args) async {
  Logger logger = const Logger(min: LoggerLevel.info);
  if (args.contains("-v")) {
    logger = logger.copyWith(min: LoggerLevel.debug);
  }

  logger.i("");
  if (args.contains("--clear")) {
    await clearTiles(logger);
  } else {
    await clearTiles(logger);
    logger.i("");
    await addTiles(logger);
  }

  final start = DateTime.now();

  logger.i("");
  logger.i("Done in ${DateTime.now().difference(start)}.");
}
