package yoeden.flutter.wear.updater;

import android.content.Context;

import yoeden.flutter.wear.TilesManager;
import yoeden.flutter.wear.tile.FlutterTileService;

public class TileUpdater {
    private final Context context;

    public TileUpdater(Context context) {
        this.context = context;
    }

    public void update() {
        TilesManager.requestUpdate(context, FlutterTileService.class);
    }
}
