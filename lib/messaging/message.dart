import 'dart:convert';
import 'dart:typed_data';

class Message {
  final String path;
  final Uint8List data;
  final String? node;
  final int? id;

  Message({
    required this.path,
    required this.data,
    this.id,
    this.node,
  });

  Map<String, dynamic> toJson() => {
        "path": path,
        "data": data,
      };

  String dataAsString() => utf8.decode(data);

  factory Message.fromJson(Map json) => Message(
        path: json['path'],
        data: json['data'],
        id: json['id'],
        node: json['node'],
      );

  factory Message.string(String path, String data) {
    return Message(
      path: path,
      data: Uint8List.fromList(utf8.encode(data)),
    );
  }
}
