import 'package:wearthat/tiles.dart';

class Padding extends TileWidget {
  final EdgeInsets padding;
  final TileWidget child;

  const Padding({
    required this.padding,
    required this.child,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__padding",
        "padding": padding.serialize(),
        "child": child.serialize(),
      };

  @override
  TileWidget build() => this;
}

class EdgeInsets {
  final double left;
  final double top;
  final double right;
  final double bottom;

  const EdgeInsets.fromLTRB(this.left, this.top, this.right, this.bottom);

  const EdgeInsets.all(double value)
      : left = value,
        top = value,
        right = value,
        bottom = value;

  const EdgeInsets.only({
    this.left = 0.0,
    this.top = 0.0,
    this.right = 0.0,
    this.bottom = 0.0,
  });

  const EdgeInsets.symmetric({
    double vertical = 0.0,
    double horizontal = 0.0,
  })  : left = horizontal,
        top = vertical,
        right = horizontal,
        bottom = vertical;

  Map<String, Object> serialize() => {
        "l": left,
        "r": right,
        "t": top,
        "b": bottom,
      };
}
