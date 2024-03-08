import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:wearthat/tiles.dart';

/// Preview of a tile
class WearTilePreview extends StatelessWidget {
  final String tile;
  final WatchDeviceAppearance device;

  const WearTilePreview({
    required this.tile,
    this.device = kDefaultWatchDevice,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    const String viewType = 'TILE_PREVIEW';

    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{
      'tile': tile,
    };

    // Update tile to make sure its fresh.
    Tiles.updateTile(tile);

    return Center(
      child: ConstrainedBox(
        constraints:
            BoxConstraints(maxHeight: device.height, maxWidth: device.width),
        child: AspectRatio(
          aspectRatio: 1,
          child: ClipRRect(
            borderRadius: device.isCircular
                ? BorderRadius.circular(300)
                : BorderRadius.zero,
            child: Container(
              // Watch background color must be black
              color: Colors.black,
              child: AndroidView(
                viewType: viewType,
                layoutDirection: TextDirection.ltr,
                creationParams: creationParams,
                creationParamsCodec: const StandardMessageCodec(),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
