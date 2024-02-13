import 'package:wear/tiles/widgets.dart';

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
}
