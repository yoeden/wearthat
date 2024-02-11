package yoeden.flutter.wear.tile;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.TaskStackBuilder;
import androidx.wear.tiles.RequestBuilders;
import androidx.wear.tiles.ResourceBuilders;
import androidx.wear.tiles.TileBuilders;
import androidx.wear.tiles.TileService;

import com.google.common.util.concurrent.ListenableFuture;

import yoeden.flutter.wear.FlutterTilesChannel;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.translation.units.ClickableWidgetTranslator;

public class FlutterTileService extends TileService {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    protected ListenableFuture<TileBuilders.Tile> onTileRequest(@NonNull RequestBuilders.TileRequest requestParams) {
        Log.i(FlutterWearTiles.Tag,"onTileRequest: " + requestParams.getState().getLastClickableId());
        final String route = requestParams.getState().getLastClickableId() == null || requestParams.getState().getLastClickableId().equals("")
                ? FlutterWearTiles.RootRoute :
                requestParams.getState().getLastClickableId();

        return FlutterTileLayout.onTileRequest(this, requestParams, route);
    }


    @NonNull
    @Override
    protected ListenableFuture<ResourceBuilders.Resources> onResourcesRequest(@NonNull RequestBuilders.ResourcesRequest requestParams) {
        Log.i(FlutterWearTiles.Tag,"onResourcesRequest");
        return FlutterTileResources.onResourcesRequest(this, requestParams);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FlutterTilesChannel.initialize(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FlutterTilesChannel.destroy();
    }
}
