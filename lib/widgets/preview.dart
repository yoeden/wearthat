import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

class WearTilePreview extends StatelessWidget {
  const WearTilePreview({super.key});

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    const String viewType = 'TILE_PREVIEW';
    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{};

    return AndroidView(
      viewType: viewType,
      layoutDirection: TextDirection.ltr,
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
    );
  }
}
