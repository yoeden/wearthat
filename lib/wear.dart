
import 'wear_platform_interface.dart';

class Wear {
  Future<String?> getPlatformVersion() {
    return WearPlatform.instance.getPlatformVersion();
  }
}
