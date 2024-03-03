import 'package:flutter/material.dart';
import 'package:wear/tiles.dart';

class PreviewTile extends StatelessWidget {
  final String tile;

  const PreviewTile({
    super.key,
    required this.tile,
  });

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: WearTilePreview(tile: tile),
      ),
    );
  }
}
