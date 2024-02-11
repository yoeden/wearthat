import 'package:wear/widgets.dart';

class Row extends TileWidget {
  final MainAxisAlignment mainAxisAlignment;
  final MainAxisSize mainAxisSize;
  final List<TileWidget> children;

  const Row({
    this.mainAxisAlignment = MainAxisAlignment.start,
    this.mainAxisSize = MainAxisSize.max,
    this.children = const [],
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__row",
        "alignment": mainAxisAlignment.index,
        "size": mainAxisSize.index,
        "children": children.map((e) => e.serialize()).toList(),
      };
}
