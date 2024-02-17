import 'dart:ui';

import 'package:wear/tiles/widgets.dart';

class ArcProgressLine extends ArcWidget {
  final ArcProgressLineStyle style;
  final ArcProgressDirection direction;
  final double length;
  final double progress;

  ArcProgressLine({
    required this.length,
    required this.progress,
    this.direction = ArcProgressDirection.clockwise,
    ArcProgressLineStyle? style,
  })  : assert(progress >= 0 && progress <= 1),
        style = style ?? const ArcProgressLineStyle();

  @override
  Map<String, Object> serialize() => {
        "type": "__arc_progress_line",
        "progress": progress * length,
        "length": length,
        "direction": direction.index,
        "style": style.toJson(),
      };

  @override
  TileWidget build() => this;
}

class ArcProgressLineStyle {
  final Color color;
  final Color? backgroundColor;
  final double thickness;

  const ArcProgressLineStyle({
    Color? color,
    double? thickness,
    this.backgroundColor,
  })  : color = color ?? const Color(0xFFFFFFFF),
        thickness = thickness ?? 2;

  Map<String, Object> toJson() => {
        "color": color.value,
        "backgroundColor": (backgroundColor ?? color.withOpacity(0.4)).value,
        "thickness": thickness,
      };
}

enum ArcProgressDirection {
  clockwise,
  counterClockwise,
}

//

class ArcLine extends ArcWidget {
  final ArcLineStyle? style;
  final double length;

  ArcLine({
    required this.length,
    this.style = const ArcLineStyle(),
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__arc_line",
        "length": length,
        "style": style!.toJson(),
      };

  @override
  TileWidget build() => this;
}

class ArcLineStyle {
  final Color color;
  final double thickness;

  const ArcLineStyle({
    Color? color,
    this.thickness = 2,
  }) : color = color ?? const Color(0xFFFFFFFF);

  Map<String, Object> toJson() => {
        "color": color.value,
        "thickness": thickness,
      };
}
