import 'package:wearthat/tiles.dart';

class Image extends TileWidget {
  final String resource;
  final BoxFit fit;

  const Image(
    this.resource, {
    this.fit = BoxFit.undefined,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__image",
        "resource": resource,
        "fit": fit.index,
      };

  @override
  TileWidget build() => this;
}

enum BoxFit {
  undefined,
  contain,
  cover,
  fill,
}
