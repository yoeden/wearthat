import 'package:flutter/services.dart';
import 'package:wear/flutter_wear_tiles_platform_interface.dart';
import 'package:wear/messaging/message.dart';

typedef MessageHandler = Future Function(Message message);

abstract class WearMessenger {
  static Future<void> send(Message message) {
    return FlutterWearTilesPlatform.instance.send(message);
  }

  static void listen(String path, MessageHandler handler) {
    _MessageClient().addOnMessageReceived(path, handler);
  }
}

class _MessageClient {
  static _MessageClient? _instance;
  factory _MessageClient() {
    _instance ??= _MessageClient._();
    return _instance!;
  }

  _MessageClient._() {
    _channel = const MethodChannel("flutter_wear_tiles_datacallback");
    _channel.setMethodCallHandler(_callbackHandler);
  }

  late final MethodChannel _channel;
  final Map<String, List<MessageHandler>> _handlers = {};

  Future<void> _callbackHandler(MethodCall call) {
    switch (call.method) {
      case "data_received":
        final args = call.arguments;
        final message = Message.fromJson(args as Map);

        _invokeForPath(message.path, message);
        _invokeForPath(".", message);

        break;
      default:
    }

    return Future.value();
  }

  void _invokeForPath(String path, Message message) {
    if (_handlers.containsKey(path)) {
      for (final handler in _handlers[path]!) {
        handler(message);
      }
    }
  }

  void addOnMessageReceived(String path, Future Function(Message message) callback) {
    if (!_handlers.containsKey(path)) {
      _handlers[path] = [callback];
      return;
    }

    if (_handlers[path]!.contains(callback)) return;

    _handlers[path]!.add(callback);
  }

  void removeOnMessageReceived(String path, Future Function(Message message) callback) {
    if (!_handlers.containsKey(path)) return;
    _handlers[path]!.remove(callback);
  }
}
