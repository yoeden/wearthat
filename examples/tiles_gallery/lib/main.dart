import 'package:flutter/material.dart';
import 'package:tiles_gallery/examples/timer/app.dart';
import 'package:tiles_gallery/preview_tile.dart';
import 'package:tiles_gallery/tiles.dart';
import 'package:wear/tiles.dart' as wear_tiles;

@pragma('vm:entry-point')
void maintile(List<String> args) {
  wear_tiles.runTiles(
    myTiles,
  );
}

void main() {
  runApp(const MyApp());
}

const List<TilePreviewItem> tiles = [
  TilePreviewItem(tile: "WeatherTile", label: "Weather"),
  TilePreviewItem(tile: "TimerTile", label: "Timer"),
  TilePreviewItem(tile: "CryptoTile", label: "Crypto"),
  TilePreviewItem(tile: "TestTile", label: "Smart home"),
  TilePreviewItem(tile: "FitnessTile", label: "FitnessTile"),
];

class TilePreviewItem {
  final String tile;
  final String label;

  const TilePreviewItem({required this.tile, required this.label});
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      routes: {
        '/TEST': (context) => TimerScreen(),
      },
      home: SafeArea(
        child: Scaffold(
          body: Builder(
            builder: (context) {
              if (Theme.of(context).platform != TargetPlatform.android) {
                return Center(
                  child: Text("Demo only support running from an android device or wear !"),
                );
              }

              return ListView.builder(
                itemBuilder: (context, index) => TextButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => PreviewTile(tile: tiles[index].tile)),
                    );
                  },
                  child: Text(
                    tiles[index].label,
                  ),
                ),
                itemCount: tiles.length,
              );
            },
          ),
        ),
      ),
    );
  }
}
