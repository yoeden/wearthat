import 'package:wear/src/widgets/keys.dart';
import 'package:wear/tile_widgets.dart';

class Column extends TileWidget {
  final MainAxisAlignment mainAxisAlignment;
  final CrossAxisAlignment crossAxisAlignment;
  final MainAxisSize mainAxisSize;
  final List<TileWidget> children;

  const Column({
    this.mainAxisAlignment = MainAxisAlignment.start,
    this.crossAxisAlignment = CrossAxisAlignment.center,
    this.mainAxisSize = MainAxisSize.max,
    this.children = const [],
  });

  @override
  Map<String, Object> serialize() => {
        kTileWidgetType: "__column",
        //TODO: Migrate all widgets to use those
        kTileWidgetMainAxisAlignment: mainAxisAlignment.index,
        kTileWidgetCrossAxisAlignment: crossAxisAlignment.index,
        kTileWidgetMainAxisSize: mainAxisSize.index,
        kTileWidgetChildren: children.map((e) => e.serialize()).toList(),
      };

  @override
  TileWidget build() => this;
}

enum MainAxisAlignment {
  start,
  end,
  center,
}

enum CrossAxisAlignment {
  start,
  end,
  center,
}

enum MainAxisSize {
  min,
  max,
}
