import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'wear_method_channel.dart';

abstract class WearPlatform extends PlatformInterface {
  /// Constructs a WearPlatform.
  WearPlatform() : super(token: _token);

  static final Object _token = Object();

  static WearPlatform _instance = MethodChannelWear();

  /// The default instance of [WearPlatform] to use.
  ///
  /// Defaults to [MethodChannelWear].
  static WearPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [WearPlatform] when
  /// they register themselves.
  static set instance(WearPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
