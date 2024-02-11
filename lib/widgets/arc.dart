import 'dart:ui';

import 'package:wear/widgets.dart';

class Arc extends TileWidget {
  final double value;
  final int? anchroAngle;
  final AnchorType? anchorType;
  final ArcStyle? style;

  const Arc({
    required this.value,
    this.anchroAngle = 0,
    this.anchorType = AnchorType.start,
    this.style,
  }) : assert(value >= 0 && value <= 1);

  @override
  Map<String, Object> serialize() => {
        "type": "__arc",
        "value": value,
        if (anchroAngle != null) "anchorAngle": anchroAngle!,
        if (style != null)
          "style": {
            if (style!.color != null) "color": style!.color!.value,
            if (style!.thickness != null) "thickness": style!.thickness,
          }
      };
}

class ArcStyle {
  final Color? color;
  final int? thickness;

  const ArcStyle({
    this.color,
    this.thickness,
  });
}

enum AnchorType {
  start,
  center,
  end,
}

// class Arc extends Widget {
//     private final int maxProgress;
//     private final int progress;
//     private final int color;
//     private final int thickness;

//     Arc(int maxProgress, int progress, int color, int thickness) {

//         this.maxProgress = maxProgress;
//         this.progress = progress;
//         this.color = color;
//         this.thickness = thickness;
//     }

//     @Override
//     LayoutElementBuilders.LayoutElement build() {
//         LayoutElementBuilders.ArcLine.Builder builder = new LayoutElementBuilders.ArcLine.Builder();

//         builder.setLength(degrees(progress / ((float) maxProgress) * 360));
//         builder.setColor(argb(color));
//         builder.setThickness(new DimensionBuilders.DpProp.Builder().setValue(thickness).build());

//         return new LayoutElementBuilders.Arc.Builder()
//                 .addContent(builder.build())
//                 //Element starts at 12 o'clock or 0 degree position in the circle
//                 .setAnchorAngle(degrees(0.0f))
//                 .setAnchorType(LayoutElementBuilders.ARC_ANCHOR_START)
//                 .build();
//     }
// }
