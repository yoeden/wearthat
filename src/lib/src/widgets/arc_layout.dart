import 'package:wear/tile_widgets.dart';

mixin ArcLayoutMixin on TileWidget {}

class ArcLayout extends TileWidget with ArcLayoutMixin {
  final List<ArcWidget> children;
  final ArcEdgesFitAlignment edgeAlignment;
  final double anchor;

  ArcLayout({
    this.children = const [],
    this.edgeAlignment = ArcEdgesFitAlignment.close,
    this.anchor = 0,
  });
  // : assert(anchor >= 0 && anchor <= 1);

  @override
  Map<String, Object> serialize() => {
        "type": "__arc_layout",
        "alignment": edgeAlignment.index,
        "anchor": anchor,
        "children": children.map((e) => e.serialize()).toList(),
      };

  @override
  TileWidget build() => this;
}

enum ArcEdgesFitAlignment {
  undefined,
  close,
  safe,
  far,
}

abstract class ArcWidget extends TileWidget {
  const ArcWidget();
}
