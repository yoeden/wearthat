import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:wear/messaging.dart';

import 'wear_platform_interface.dart';

/// An implementation of [FlutterWearTilesPlatform] that uses method channels.
class MethodChannelFlutterWearTiles extends FlutterWearTilesPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('wear/manager');

  @override
  Future<void> requestUpdate(String name) {
    return methodChannel.invokeMethod("update", name);
  }

  @override
  Future<void> logd(String info) {
    return methodChannel.invokeMethod("logd", info);
  }
}
