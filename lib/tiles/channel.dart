import 'package:flutter/services.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class TilesChannelPlatform extends PlatformInterface {
  static final Object _token = Object();

  TilesChannelPlatform() : super(token: _token);

  static final TilesChannelPlatform _instance = _TilesChannel();

  /// The default instance of [TilesChannelPlatform] to use.
  ///
  /// Defaults to [TilesChannelPlatform].
  static TilesChannelPlatform get instance => _instance;
}

class _TilesChannel extends TilesChannelPlatform {
  late final MethodChannel _channel;

  _TilesChannel() {
    _channel = const MethodChannel("wear/tiles");
    _channel.setMethodCallHandler(_callbackHandler);
  }

  Future<dynamic> _callbackHandler(MethodCall call) async {
    switch (call.method) {
      case "requestResources":
        break;
      case "requestLayout":
        break;
    }

    throw UnimplementedError('${call.method}() has not been implemented.');
  }
}
