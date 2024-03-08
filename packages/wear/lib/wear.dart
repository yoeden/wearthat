import 'package:wearthat/communication.dart';
import 'package:wearthat/src/communication/client.dart';

/// Utils class with generic functionally revolving around wear
abstract class Wear {
  static final WearCommunicationChannel _instance = WearCommunicationChannel();

  static Future<List<int?>?> send(WearMessage message) {
    return _instance.send(message);
  }

  static Future<List<WearNode>> getNodes() {
    return _instance.getNodes();
  }

  static void listen(String path, WearMessageCallback handler) {
    _instance.addOnMessageReceived(path, handler);
  }

  static void removeListener(String path, WearMessageCallback handler) {
    _instance.removeOnMessageReceived(path, handler);
  }
}
