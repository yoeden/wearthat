import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:wearthat/communication.dart';
import 'package:wearthat/src/communication/listeners_manager.dart';

/// This class represents the communication channel for wearables.
class WearCommunicationChannel {
  late final MethodChannel _channel;
  final WearMessagesManager _messagesListenersManager = WearMessagesManager();

  WearCommunicationChannel() {
    _channel = const MethodChannel("wear/communication");
    _channel.setMethodCallHandler(_callbackHandler);
  }

  /// Retrieves a list of connected WearNodes.
  Future<List<WearNode>> getNodes() async {
    final raw = await _channel.invokeMethod("getNodes");
    final map = jsonDecode(raw) as List;
    return map.map((e) => WearNode.fromJson(e)).toList();
  }

  /// Sends a WearMessage to connected nodes and returns a list of results.
  Future<List<int?>?> send(WearMessage message) async {
    final result = await _channel.invokeMethod<List<Object?>>("send", jsonEncode(message.toJson()));

    return result!.map((e) => e as int).toList();
  }

  /// Handles the method call from the platform side.
  Future<void> _callbackHandler(MethodCall call) {
    switch (call.method) {
      case "onMessageReceived":
        final args = call.arguments;
        final message = WearMessage.fromJson(args as Map);

        _messagesListenersManager.invoke(message);

        break;
      default:
    }

    return Future.value();
  }

  /// Adds a [callback] for incoming messages for the specified [path].
  /// Note: You can also listen on the path "." for all paths.
  ///
  /// Dont forget to remove the callback with [removeOnMessageReceived] once finished.
  void addOnMessageReceived(String path, WearMessageCallback callback) => _messagesListenersManager.addOnMessageReceived(path, callback);

  /// Removes the callback for the specified path.
  void removeOnMessageReceived(String path, WearMessageCallback callback) => _messagesListenersManager.removeOnMessageReceived(path, callback);
}
