import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:wear/communication/wear_node.dart';
import 'package:wear/messaging.dart';

abstract class WearCommunication {
  static final _MessageClient _instance = _MessageClient();

  static Future<List<int?>?> send(WearMessage message) {
    return _instance.send(message);
  }

  static Future<List<WearNode>> getNodes() {
    return _instance.getNodes();
  }

  static void listen(String path, WearMessageCallback handler) {
    _MessageClient().addOnMessageReceived(path, handler);
  }
}

class _MessageClient {
  late final MethodChannel _channel;
  final Map<String, List<WearMessageCallback>> _handlers = {};

  _MessageClient() {
    _channel = const MethodChannel("wear/communication");
    _channel.setMethodCallHandler(_callbackHandler);
  }

  Future<List<int?>?> send(WearMessage message) {
    return _channel.invokeMethod<List<int?>>("send", jsonEncode(message.toJson()));
  }

  Future<List<WearNode>> getNodes() async {
    final raw = await _channel.invokeMethod("getNodes");
    final map = jsonDecode(raw) as List;
    return map.map((e) => WearNode.fromJson(e)).toList();
  }

  Future<void> _callbackHandler(MethodCall call) {
    switch (call.method) {
      case "onMessageReceived":
        final args = call.arguments;
        final message = WearMessage.fromJson(args as Map);

        _invokeForPath(message.path, message);
        _invokeForPath(".", message);

        break;
      default:
    }

    return Future.value();
  }

  void _invokeForPath(String path, WearMessage message) {
    if (_handlers.containsKey(path)) {
      for (final handler in _handlers[path]!) {
        handler(message);
      }
    }
  }

  void addOnMessageReceived(String path, Future Function(WearMessage message) callback) {
    if (!_handlers.containsKey(path)) {
      _handlers[path] = [callback];
      return;
    }

    if (_handlers[path]!.contains(callback)) return;

    _handlers[path]!.add(callback);
  }

  void removeOnMessageReceived(String path, Future Function(WearMessage message) callback) {
    if (!_handlers.containsKey(path)) return;
    _handlers[path]!.remove(callback);
  }
}
