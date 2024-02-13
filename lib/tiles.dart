import 'package:wear/src/tiles/channels/manager.dart';

//
export '/src/tiles/engine.dart';
export '/src/tiles/routing.dart';
export '/src/tiles/builder.dart';
export '/src/tiles/service.dart';
export '/src/tiles/host.dart';
export '/src/tiles/tile.dart';
export '/src/tiles/app_tiles.dart';

class Tiles {
  static final WearManagerChannel _channel = WearManagerChannel();

  Future<void> updateTile(String tile) {
    return _channel.updateTile(tile);
  }
}
