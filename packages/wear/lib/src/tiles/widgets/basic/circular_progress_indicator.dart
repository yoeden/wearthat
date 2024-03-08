import 'package:wearthat/src/tiles/widgets/properties.dart';
import 'package:wearthat/tiles.dart';

class CircularProgressIndicator extends TileWidget {
  final double progress;
  final double? startAngle;
  final double? endAngle;
  final double? thickness;

  CircularProgressIndicator({
    required this.progress,
    this.endAngle,
    this.startAngle,
    this.thickness,
  });

  @override
  TileWidget build() {
    // TODO: implement build
    throw UnimplementedError();
  }

  @override
  Map<String, Object> serialize() => {
        kTileWidgetType: '__circularprogressindicator',
        'progress': progress,
        if (startAngle != null) 'startAngle': startAngle!,
        if (endAngle != null) 'endAngle': endAngle!,
      };
}
