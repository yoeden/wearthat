import 'package:wear/communication.dart';
import 'package:wear/src/communication/client.dart';

abstract class Wear {
  static final MessageClient _instance = MessageClient();

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