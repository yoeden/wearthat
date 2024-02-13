import 'package:wear/src/tiles/channels/manager.dart';

class Tiles {
  static final WearManagerChannel _channel = WearManagerChannel();

  Future<void> updateTile(String tile) {
    return _channel.updateTile(tile);
  }
}
