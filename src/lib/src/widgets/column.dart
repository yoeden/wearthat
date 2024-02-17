import 'package:wear/tiles/widgets.dart';

class Column extends TileWidget {
  final MainAxisAlignment mainAxisAlignment;
  final MainAxisSize mainAxisSize;
  final List<TileWidget> children;

  const Column({
    this.mainAxisAlignment = MainAxisAlignment.start,
    this.mainAxisSize = MainAxisSize.max,
    this.children = const [],
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__column",
        "alignment": mainAxisAlignment.index,
        "size": mainAxisSize.index,
        "children": children.map((e) => e.serialize()).toList(),
      };

  @override
  TileWidget build() => this;
}

enum MainAxisAlignment {
  start,
  end,
  center,
}

enum MainAxisSize {
  min,
  max,
}
