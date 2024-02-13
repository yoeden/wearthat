import 'package:wear/tiles/widgets.dart';

class ArcText extends TileWidget {
  final String text;
  final TextStyle? style;

  ArcText(
    this.text, {
    this.style,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__arc_text",
        "text": text,
        if (style != null) "style": style!.toJson(),
      };
}
