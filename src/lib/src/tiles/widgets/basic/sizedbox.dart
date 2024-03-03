import 'package:wear/tiles.dart';

class SizedBox extends TileWidget {
  final double? height;
  final double? width;
  final TileWidget? child;

  const SizedBox({
    this.height,
    this.width,
    this.child,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__sizedbox",
        if (height != null) "height": height!,
        if (width != null) "width": width!,
        if (child != null) "child": child!.serialize(),
      };

  @override
  TileWidget build() => this;
}
