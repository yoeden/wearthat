import 'dart:ui';

import 'package:wear/tiles/widgets.dart';

class DecoratedBox extends TileWidget {
  final BoxDecoration decoration;
  final TileWidget child;

  DecoratedBox({
    required this.decoration,
    required this.child,
  });

  @override
  Map<String, Object> serialize() => {
        'type': "__decoratedbox",
        'decoration': decoration.serialize(),
        'child': child.serialize(),
      };
}

class BoxDecoration {
  final Color? color;
  final Border? border;
  final double? borderRadius;

  BoxDecoration({
    this.color,
    this.borderRadius,
    this.border,
  });

  Map<String, Object> serialize() => {
        if (color != null) 'color': color!.value,
        if (border != null) 'border': border!.serialize(),
        if (borderRadius != null) 'borderRadius': borderRadius!,
      };
}

class Border {
  final Color? color;
  final double? width;

  Border({
    this.color,
    this.width,
  });

  Map<String, Object> serialize() => {
        if (color != null) 'color': color!.value,
        if (width != null) 'width': width!,
      };
}
