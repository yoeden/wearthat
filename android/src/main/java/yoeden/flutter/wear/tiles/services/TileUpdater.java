package yoeden.flutter.wear.tiles.services;

import android.content.Context;
import android.util.Log;

import androidx.wear.tiles.TileService;

import yoeden.flutter.wear.FlutterWearTiles;

public class TileUpdater {
    private final Context context;

    public TileUpdater(Context context) {
        this.context = context;
    }

    public void update() {
        requestUpdate(context, FlutterTileService.class);
    }

    private static void requestUpdate(Context context, Class<? extends TileService> tile) {
        TileService.getUpdater(context).requestUpdate(tile);
    }
}
