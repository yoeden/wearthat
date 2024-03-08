import 'dart:math';

import 'package:wearthat/tiles.dart';
import 'package:flutter/material.dart' as m;

class DebugRainbow extends TileWidget {
  final TileWidget child;
  final double opacity;

  DebugRainbow({
    required this.child,
    this.opacity = 1,
  }) : assert(opacity >= 0 && opacity <= 1);

  @override
  TileWidget build() {
    return DecoratedBox(
      decoration: BoxDecoration(
        color: m.Colors.primaries[Random().nextInt(m.Colors.primaries.length)]
            .withOpacity(opacity),
      ),
      child: child,
    );
  }
}
