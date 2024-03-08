/// Represents a node in the wear network
class WearNode {
  /// Node identifier
  final String id;

  /// Node name
  final String displayName;

  /// Node is nearby
  final bool isNearby;

  /// Constructor for WearNode
  WearNode({
    required this.id,
    required this.displayName,
    required this.isNearby,
  });

  /// Factory method to create a WearNode instance from a map
  factory WearNode.fromJson(Map map) => WearNode(
        id: map['id'],
        displayName: map['displayName'],
        isNearby: map['isNearby'],
      );

  @override
  String toString() {
    return "id: $id, name: $displayName, isNearby: $isNearby";
  }
}
