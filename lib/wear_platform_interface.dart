import 'package:wear/messaging.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:wear/wear_method_channel.dart';

abstract class FlutterWearTilesPlatform extends PlatformInterface {
  /// Constructs a FlutterWearTilesPlatform.
  FlutterWearTilesPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterWearTilesPlatform _instance = MethodChannelFlutterWearTiles();

  /// The default instance of [FlutterWearTilesPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterWearTiles].
  static FlutterWearTilesPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterWearTilesPlatform] when
  /// they register themselves.
  static set instance(FlutterWearTilesPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> requestUpdate(String name) {
    throw UnimplementedError('requestUpdate() has not been implemented.');
  }

  Future<void> logd(String info) {
    throw UnimplementedError('logd() has not been implemented.');
  }
}
