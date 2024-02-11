package yoeden.flutter.wear;

import android.content.Context;
import android.util.Log;

import androidx.wear.tiles.TileService;

public class TilesManager {
    public static void requestUpdate(Context context,Class<? extends TileService>...tiles)
    {
        for (Class<? extends TileService> tile: tiles) {
            TileService.getUpdater(context).requestUpdate(tile);
        }

        Log.d(FlutterWearTiles.Tag,String.format("Update %d tiles",tiles.length));
    }
}
