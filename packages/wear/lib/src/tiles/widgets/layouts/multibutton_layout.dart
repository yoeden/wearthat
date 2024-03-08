import 'package:wear/tiles.dart';

class MultiButtonLayout extends TileWidget {
  final List<TileWidget> children;

  MultiButtonLayout({
    required this.children,
  });

  @override
  TileWidget build() => this;

  @override
  Map<String, Object> serialize() => {
        'type': '__multibutton_layout',
        'children': children.map((e) => e.serialize()).toList(),
      };
}
