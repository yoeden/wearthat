import 'package:wearthat/tiles.dart';

class Stack extends TileWidget {
  final List<TileWidget> children;

  const Stack({
    this.children = const [],
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__stack",
        "children": children.map((e) => e.serialize()).toList(),
      };

  @override
  TileWidget build() => this;
}
