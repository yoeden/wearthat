import 'package:wear/src/tiles/widgets/properties.dart';
import 'package:wear/tiles.dart';

class Row extends TileWidget {
  final MainAxisAlignment mainAxisAlignment;
  final CrossAxisAlignment crossAxisAlignment;
  final MainAxisSize mainAxisSize;
  final List<TileWidget> children;

  const Row({
    this.mainAxisAlignment = MainAxisAlignment.start,
    this.crossAxisAlignment = CrossAxisAlignment.center,
    this.mainAxisSize = MainAxisSize.max,
    this.children = const [],
  });

  @override
  Map<String, Object> serialize() => {
        kTileWidgetType: "__row",
        kTileWidgetMainAxisAlignment: mainAxisAlignment.index,
        kTileWidgetCrossAxisAlignment: crossAxisAlignment.index,
        kTileWidgetMainAxisSize: mainAxisSize.index,
        kTileWidgetChildren: children.map((e) => e.serialize()).toList(),
      };

  @override
  TileWidget build() => this;
}
