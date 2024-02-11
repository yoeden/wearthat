package yoeden.flutter.wear.tile.dtos;

import yoeden.flutter.wear.translation.FlutterTileWidgetParcel;

public class RootLayoutTile {
    private final FlutterTileWidgetParcel _tile;
    private final TileFreshness _freshness;

    public RootLayoutTile(FlutterTileWidgetParcel tile, TileFreshness freshness) {
        _tile = tile;
        _freshness = freshness;
    }

    public FlutterTileWidgetParcel getTile() {
        return _tile;
    }

    public TileFreshness getFreshness() {
        return _freshness;
    }
}

