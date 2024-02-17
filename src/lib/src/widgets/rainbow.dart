import 'dart:math';

import 'package:wear/tiles/widgets.dart';
import 'package:flutter/material.dart' as m;

class Rainbow extends TileWidget {
  final TileWidget child;
  final double opacity;

  Rainbow({
    required this.child,
    this.opacity = 1,
  }) : assert(opacity >= 0 && opacity <= 1);

  @override
  TileWidget build() {
    return DecoratedBox(
      decoration: BoxDecoration(
        color: m.Colors.primaries[Random().nextInt(m.Colors.primaries.length)].withOpacity(opacity),
      ),
      child: child,
    );
  }
}
