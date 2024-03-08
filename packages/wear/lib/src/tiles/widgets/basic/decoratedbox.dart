import 'dart:ui';

import 'package:flutterwear/tiles.dart';

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

  @override
  TileWidget build() => this;
}

class BoxDecoration {
  final Color? color;
  final Border? border;
  final double? borderRadius;
  final EdgeInsets? padding;

  BoxDecoration({
    this.color,
    this.borderRadius,
    this.border,
    this.padding,
  });

  Map<String, Object> serialize() => {
        if (color != null) 'color': color!.value,
        if (border != null) 'border': border!.serialize(),
        if (borderRadius != null) 'borderRadius': borderRadius!,
        if (padding != null) 'padding': padding!.serialize(),
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
