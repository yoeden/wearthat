import 'package:wear/tiles.dart';

abstract class AppTiles {
  const AppTiles();

  List<TileService> getTiles();
}
