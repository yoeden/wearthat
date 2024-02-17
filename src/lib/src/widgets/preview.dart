import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class WearTilePreview extends StatelessWidget {
  final String tile;

  const WearTilePreview({super.key, required this.tile});

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    const String viewType = 'TILE_PREVIEW';
    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{
      'tile': tile,
    };

    return Container(
      color: Colors.black,
      child: AndroidView(
        viewType: viewType,
        layoutDirection: TextDirection.ltr,
        creationParams: creationParams,
        creationParamsCodec: const StandardMessageCodec(),
      ),
    );
  }
}
