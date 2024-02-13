import 'dart:convert';
import 'dart:typed_data';

class WearMessage {
  final String path;
  final Uint8List data;
  final String? node;
  final int? id;

  WearMessage({
    required this.path,
    required this.data,
    this.id,
    this.node,
  });

  Map<String, dynamic> toJson() => {
        "path": path,
        "data": data,
        'id': id,
        'node': node,
      };

  String dataAsString() => utf8.decode(data);

  factory WearMessage.fromJson(Map json) => WearMessage(
        path: json['path'],
        data: json['data'],
        id: json['id'],
        node: json['node'],
      );

  factory WearMessage.string(String path, String data) {
    return WearMessage(
      path: path,
      data: Uint8List.fromList(utf8.encode(data)),
    );
  }
}
