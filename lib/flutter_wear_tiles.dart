import 'wear_platform_interface.dart';

class FlutterWearTiles {
  static Future<void> requestUpdate(String name) {
    return FlutterWearTilesPlatform.instance.requestUpdate(name);
  }

  static Future<void> logd(String info) {
    return FlutterWearTilesPlatform.instance.logd(info);
  }
}
