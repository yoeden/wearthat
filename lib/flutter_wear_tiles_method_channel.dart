import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:wear/messaging.dart';

import 'flutter_wear_tiles_platform_interface.dart';

/// An implementation of [FlutterWearTilesPlatform] that uses method channels.
class MethodChannelFlutterWearTiles extends FlutterWearTilesPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_wear_tiles');

  @override
  Future<void> send(Message message) {
    return methodChannel.invokeMethod("send", message.toJson());
  }

  @override
  Future<void> requestUpdate(String name) {
    return methodChannel.invokeMethod("requestUpdate", name);
  }

  @override
  Future<void> logd(String info) {
    return methodChannel.invokeMethod("logd", info);
  }
}
