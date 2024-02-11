import 'package:wear/widgets.dart';

class ArcDualProgressLayout extends TileWidgetBuilder with ArcLayoutMixin {
  final double length;
  final double iconSize;

  final double left;
  final ArcProgressLineStyle? leftStyle;
  final TileWidget? leftIcon;

  final double right;
  final ArcProgressLineStyle? rightStyle;
  final TileWidget? rightIcon;

  ArcDualProgressLayout({
    required this.length,
    required this.left,
    required this.right,
    this.iconSize = 25,
    this.leftStyle,
    this.leftIcon,
    this.rightStyle,
    this.rightIcon,
  });

  @override
  TileWidget build() {
    throw UnimplementedError();
    // final actualLength = (length + iconSize / 360) - 0.01;
    // final space = (1 - ((actualLength * 2))) / 2;
    // final topSpace = space / 2;

    // return ArcLayout(
    //   anchor: topSpace,
    //   edgeAlignment: ArcEdgesFitAlignment.safe,
    //   children: [
    //     ArcProgressLine(
    //       length: length,
    //       progress: right,
    //       direction: ArcProgressDirection.counterClockwise,
    //       style: rightStyle,
    //     ),
    //     if (rightIcon != null) ...[
    //       ArcSpacer(length: 0.01),
    //       SizedBox(
    //         width: iconSize,
    //         height: iconSize,
    //         child: rightIcon,
    //       ),
    //     ],
    //     ArcSpacer(length: space),
    //     if (leftIcon != null) ...[
    //       SizedBox(
    //         width: iconSize,
    //         height: iconSize,
    //         child: leftIcon,
    //       ),
    //       ArcSpacer(length: 0.01),
    //     ],
    //     ArcProgressLine(
    //       length: length,
    //       progress: left,
    //       direction: ArcProgressDirection.clockwise,
    //       style: leftStyle,
    //     ),
    //   ],
    // );
  }
}
