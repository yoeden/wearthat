import 'package:flutter/services.dart';

class WearManagerChannel {
  final MethodChannel _channel = const MethodChannel("wear/manager");

  Future<void> updateTile(String name) {
    return _channel.invokeMethod("update", name);
  }
}
