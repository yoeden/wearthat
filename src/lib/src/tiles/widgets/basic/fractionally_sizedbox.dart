import 'package:wear/tiles.dart';

class FractionallySizedBox extends TileWidget {
  final double? widthFactor;
  final double? heightFactor;

  FractionallySizedBox({
    this.widthFactor,
    this.heightFactor,
  })  : assert(widthFactor == null || (widthFactor >= 0 && widthFactor <= 1)),
        assert(heightFactor == null || (heightFactor >= 0 && heightFactor <= 1));

  @override
  Map<String, Object> serialize() => {
        "type": "__fractionallySizedBox",
        if (widthFactor != null) "width": widthFactor!,
        if (heightFactor != null) "height": heightFactor!,
      };

  @override
  TileWidget build() => this;
}
