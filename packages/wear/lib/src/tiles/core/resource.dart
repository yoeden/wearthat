import 'dart:ui' as ui;

import 'package:flutter/services.dart';

/// Represents a resource for a tile.
///
/// Each image or media should have a unique identifier and be defined before layout can be requested.
class TileResource {
  final String id;
  final Uint8List data;

  TileResource(this.id, this.data);
}

abstract class TileResources {
  /// Creating a tile resource from assets
  ///
  /// This will throw if the asset does not exist.
  static TileResourceProvider asset(String asset) =>
      AssetTileResourceProvider(asset);

  /// Creating a tile resource from an existing image.
  static TileResourceProvider image(Future<ui.Image> image) =>
      ImageTileResourceProvider(image);

  /// Creating a tile resource from a network resource.
  ///
  /// This will throw if the [url] doesn't contain a valid image in its response.
  static TileResourceProvider network(String url,
          {Map<String, String>? headers}) =>
      NetworkTileResourceProvider(url, headers: headers);
}

/// Represents a resource provider for a tile.
///
/// See:
/// * [AssetTileResourceProvider] Provides a resource from an asset.
/// * [ImageTileResourceProvider] Provides a resource from an image.
/// * [NetworkTileResourceProvider] Provides a resource from an network.
abstract class TileResourceProvider {
  const TileResourceProvider();

  /// Asynchronous method to resolve the resource and return the binary data.
  Future<Uint8List> resolve();
}

/// Provider for asset tile resources.
class AssetTileResourceProvider extends TileResourceProvider {
  final String asset; // Asset path

  /// Constructor for AssetTileResourceProvider
  const AssetTileResourceProvider(this.asset);

  @override
  Future<Uint8List> resolve() {
    // Load the asset and convert it to Uint8List
    return rootBundle.load(asset).then((value) => value.buffer.asUint8List());
  }
}

/// Provider for image tile resources.
class ImageTileResourceProvider extends TileResourceProvider {
  final Future<ui.Image> image; // Image data

  /// Constructor for ImageTileResourceProvider
  ImageTileResourceProvider(this.image);

  @override
  Future<Uint8List> resolve() => image.then(
        (value) => value.toByteData(format: ui.ImageByteFormat.png).then(
              (value) => value!.buffer.asUint8List(),
            ),
      );
}

class NetworkTileResourceProvider extends TileResourceProvider {
  final String url;
  final Map<String, String>? headers;

  NetworkTileResourceProvider(this.url, {this.headers});

  @override
  Future<Uint8List> resolve() {
    // TODO: implement resolve
    throw UnimplementedError();
  }
}
