import 'package:flutter/services.dart';
import 'package:wear/messaging.dart';

class MessageClient {
  static MessageClient? _instance;
  factory MessageClient() {
    _instance ??= MessageClient._();
    return _instance!;
  }

  MessageClient._() {
    _channel = const MethodChannel("flutter_wear_tiles_datacallback");
    _channel.setMethodCallHandler(_callbackHandler);
  }

  late final MethodChannel _channel;
  Future Function(Message message)? _callback;

  Future<void> _callbackHandler(MethodCall call) {
    switch (call.method) {
      case "data_received":
        final args = call.arguments;
        _callback?.call(Message.fromJson(args as Map));
        break;
      default:
    }

    return Future.value();
  }

  void setOnMessageReceived(Future Function(Message message) callback) {
    _callback = callback;
  }
}
