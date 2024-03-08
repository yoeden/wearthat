import 'package:wearthat/src/tiles/channels/manager.dart';

// TODO: Orgnize this
export 'src/tiles/core/resource.dart';
export 'src/tiles/core/engine.dart';
export 'src/tiles/core/builder.dart';
export 'src/tiles/core/service.dart';
export 'src/tiles/core/host.dart';
export 'src/tiles/tile.dart';
export 'src/tiles/freshness.dart';
export 'src/tiles/widgets/widgets.dart';

export 'src/tiles/preview/preview.dart';

export 'package:flutter/material.dart' show Colors;

class Tiles {
  static final WearManagerChannel _channel = WearManagerChannel();

  static Future<void> updateTile(String tile) {
    return _channel.updateTile(tile);
  }
}
