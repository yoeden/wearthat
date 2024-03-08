import 'package:flutterwear/communication.dart';

/// Manages the message handlers for WearMessages.
class WearMessagesManager {
  final Map<String, List<WearMessageCallback>> _handlers = {};

  /// Invokes the message handler for the specified WearMessage.
  void invoke(WearMessage message) {
    _invokeForPath(message.path, message);
    _invokeForPath(".", message);
  }

  /// Invokes the message handlers for the specified path and WearMessage.
  void _invokeForPath(String path, WearMessage message) {
    if (_handlers.containsKey(path)) {
      for (final handler in _handlers[path]!) {
        handler(message);
      }
    }
  }

  /// Adds a message handler for the specified path.
  void addOnMessageReceived(String path, WearMessageCallback callback) {
    if (!_handlers.containsKey(path)) {
      _handlers[path] = [callback];
      return;
    }

    if (_handlers[path]!.contains(callback)) return;

    _handlers[path]!.add(callback);
  }

  /// Removes the message handler for the specified path.
  void removeOnMessageReceived(String path, WearMessageCallback callback) {
    if (!_handlers.containsKey(path)) return;
    _handlers[path]!.remove(callback);
  }
}
