import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'wear_platform_interface.dart';

/// An implementation of [WearPlatform] that uses method channels.
class MethodChannelWear extends WearPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('wear');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
