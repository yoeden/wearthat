import 'dart:ui';

import 'package:wearthat/tiles.dart';

class AppIcon extends TileWidget {
  final Color? background;

  AppIcon({this.background});

  @override
  Map<String, Object> serialize() => {
        "type": "__appicon",
        if (background != null) "background": background!.value,
      };

  @override
  TileWidget build() => this;
}
