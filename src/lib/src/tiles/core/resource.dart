import 'dart:typed_data';
import 'dart:ui' as ui;

import 'package:flutter/services.dart';

class TileResource {
  final String id;
  final Uint8List data;

  TileResource(this.id, this.data);
}

abstract class TileResources {
  static TileResourceProvider asset(String asset) => AssetTileResourceProvider(asset);
  static TileResourceProvider image(Future<ui.Image> image) => ImageTileResourceProvider(image);
}

abstract class TileResourceProvider {
  const TileResourceProvider();

  Future<Uint8List> resolve();
}

class AssetTileResourceProvider extends TileResourceProvider {
  final String asset;

  const AssetTileResourceProvider(this.asset);

  @override
  Future<Uint8List> resolve() {
    return rootBundle.load(asset).then((value) => value.buffer.asUint8List());
  }
}

class ImageTileResourceProvider extends TileResourceProvider {
  final Future<ui.Image> image;

  ImageTileResourceProvider(this.image);

  @override
  Future<Uint8List> resolve() => image.then(
        (value) => value.toByteData(format: ui.ImageByteFormat.png).then(
              (value) => value!.buffer.asUint8List(),
            ),
      );
}
