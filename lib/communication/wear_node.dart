class WearNode {
  final String id;
  final String displayName;
  final bool isNearby;

  WearNode({required this.id, required this.displayName, required this.isNearby});

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
