import 'package:tiles_gallery/examples/crypto/tile.dart';
import 'package:tiles_gallery/examples/fitness/tile.dart';
import 'package:tiles_gallery/examples/smarthome/tile.dart';
import 'package:tiles_gallery/examples/timer/tile.dart';
import 'package:tiles_gallery/examples/weather/tile.dart';
import 'package:flutterwear/tiles.dart';
import 'package:flutter/material.dart' as m;

final myTiles = [
  TileService(
    name: "WeatherTile",
    routes: {
      '/': () => WeatherTile(),
    },
  ),
  TileService(
    name: "TimerTile",
    routes: {
      '/': () => TimerTile(),
    },
  ),
  TileService(
    name: "CryptoTile",
    routes: {
      '/': () => CryptoTile(),
    },
  ),
  TileService(
    name: "TestTile",
    routes: {
      '/': () => OnOffTile(),
      '/a': () => MyTile(),
    },
  ),
  TileService(
    name: "FitnessTile",
    routes: {
      '/': () => FitnessTile(),
    },
  )
];
