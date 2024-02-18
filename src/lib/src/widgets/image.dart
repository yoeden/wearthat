import 'package:wear/tile_widgets.dart';

class Image extends TileWidget {
  final ImageProvider image;
  final BoxFit fit;

  const Image({
    required this.image,
    this.fit = BoxFit.undefined,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__image",
        "provider": image.serialize(),
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

abstract class ImageProvider {
  const ImageProvider();

  Map<String, Object> serialize();
}

class AssetImageProvider extends ImageProvider {
  final String path;

  const AssetImageProvider(this.path);

  @override
  Map<String, Object> serialize() => {
        "type": "asset",
        "path": path,
      };
}
