package yoeden.flutter.wear.SORT.tiles.services;

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

    private static void requestUpdate(Context context,Class<? extends TileService>...tiles)
    {
        for (Class<? extends TileService> tile: tiles) {
            TileService.getUpdater(context).requestUpdate(tile);
        }

        Log.d(FlutterWearTiles.Tag,String.format("Update %d tiles",tiles.length));
    }
}
