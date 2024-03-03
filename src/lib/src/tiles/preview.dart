import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:wear/tiles.dart';

const kDefaultWatchDevice = WatchDeviceOptions(
  width: 225,
  height: 225,
  isCircular: true,
);

class WatchDeviceOptions {
  final double width;
  final double height;
  final bool isCircular;

  const WatchDeviceOptions({
    required this.width,
    required this.height,
    required this.isCircular,
  });
}

class WearTilePreview extends StatelessWidget {
  final String tile;
  final WatchDeviceOptions device;

  const WearTilePreview({
    super.key,
    required this.tile,
    this.device = kDefaultWatchDevice,
  });

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    const String viewType = 'TILE_PREVIEW';
    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{
      'tile': tile,
    };

    //
    Tiles.updateTile(tile);

    return Center(
      child: ConstrainedBox(
        constraints: BoxConstraints(maxHeight: device.height, maxWidth: device.width),
        child: AspectRatio(
          aspectRatio: 1,
          child: ClipRRect(
            borderRadius: device.isCircular ? BorderRadius.circular(300) : BorderRadius.zero,
            child: Container(
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
