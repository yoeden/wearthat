import 'dart:convert';
import 'dart:typed_data';

/// Represents a message to be sent to or from a wearable device.
class WearMessage {
  /// Creates a WearMessage from path and data string.
  factory WearMessage.string(String path, String data, {String? node, int? id}) {
    return WearMessage(
      path: path,
      data: Uint8List.fromList(utf8.encode(data)),
      id: id,
      node: node,
    );
  }

  /// The path of the message.
  final String path;

  /// The data to be sent.
  final Uint8List data;

  /// The node to which the message will be sent.
  final String? node;

  /// The unique identifier of the message.
  final int? id;

  /// Constructor for WearMessage.
  WearMessage({
    required this.path,
    required this.data,
    this.id,
    this.node,
  });

  /// Converts WearMessage to a JSON object.
  Map<String, dynamic> toJson() => {
        "path": path,
        "data": data,
        'id': id,
        'node': node,
      };

  /// Converts the data to a string using UTF-8 decoding.
  String dataAsString() => utf8.decode(data);

  /// Creates a WearMessage from a JSON object.
  factory WearMessage.fromJson(Map json) => WearMessage(
        path: json['path'],
        data: json['data'],
        id: json['id'],
        node: json['node'],
      );
}
