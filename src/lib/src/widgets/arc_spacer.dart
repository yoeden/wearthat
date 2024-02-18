import 'package:wear/tile_widgets.dart';

class ArcSpacer extends ArcWidget {
  final double length;

  ArcSpacer({
    required this.length,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__arc_spacer",
        "length": length,
      };

  @override
  TileWidget build() => this;
}
