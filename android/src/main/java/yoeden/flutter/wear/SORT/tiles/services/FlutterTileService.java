package yoeden.flutter.wear.SORT.tiles.services;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.wear.tiles.ColorBuilders;
import androidx.wear.tiles.DimensionBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;
import androidx.wear.tiles.RequestBuilders;
import androidx.wear.tiles.ResourceBuilders;
import androidx.wear.tiles.TileBuilders;
import androidx.wear.tiles.TileService;
import androidx.wear.tiles.TimelineBuilders;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import yoeden.flutter.wear.FlutterTilesChannel;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.SORT.tiles.channel.Tiles;
import yoeden.flutter.wear.SORT.tiles.channel.TilesChannel;
import yoeden.flutter.wear.base.engine.FlutterEngineInstance;

public class FlutterTileService extends TileService {

    private TilesChannel _channel;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    protected ListenableFuture<TileBuilders.Tile> onTileRequest(@NonNull RequestBuilders.TileRequest requestParams) {

//        return Futures.immediateFuture(new TileBuilders.Tile.Builder()
//                .setResourcesVersion(FlutterWearTiles.ResourceVersion)
//                .setTimeline(
//                        TimelineBuilders.Timeline.fromLayoutElement(
//                                new LayoutElementBuilders.Text.Builder()
//                                        .setText("Kaka")
//                                        .build()))
//                .build()
//        );

        Log.i(FlutterWearTiles.Tag, "onTileRequest: " + requestParams.getState().getLastClickableId());
        final String route = requestParams.getState().getLastClickableId() == null || requestParams.getState().getLastClickableId().equals("")
                ? FlutterWearTiles.RootRoute :
                requestParams.getState().getLastClickableId();

        return FlutterTileLayout.onTileRequest(this, _channel, requestParams, route);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    protected ListenableFuture<ResourceBuilders.Resources> onResourcesRequest(@NonNull RequestBuilders.ResourcesRequest p) {
        Log.i(FlutterWearTiles.Tag, String.format("onResourcesRequest (Ids: %d)", p.getResourceIds().size()));

        return Futures.immediateFuture(new ResourceBuilders.Resources.Builder().build());
//
//        return Futures.immediateFuture(new ResourceBuilders.Resources.Builder()
//                .setVersion(FlutterWearTiles.ResourceVersion)
//                        .addIdToImageMapping("__tile_error_image", new ResourceBuilders.ImageResource.Builder()
//                                .setAndroidResourceByResId(
//                                        new ResourceBuilders.AndroidImageResourceByResId.Builder()
//                                                .setResourceId(R.drawable.error)
//                                                .build()
//                                ).build()
//                        )
//                        .addIdToImageMapping("__app_icon", new ResourceBuilders.ImageResource.Builder()
//                                .setAndroidResourceByResId(
//                                        new ResourceBuilders.AndroidImageResourceByResId.Builder()
//                                                .setResourceId(getApplicationContext().getResources().getIdentifier("ic_launcher", "mipmap", getApplicationContext().getPackageName()))
//                                                .build()
//                                ).build()
//                        )
//                .build()
//        );

        //return FlutterTileResources.onResourcesRequest(this, _channel, p);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(FlutterWearTiles.Tag, "onCreate (FlutterTileService)");

        FlutterEngineInstance.initialize(this);
        _channel = Tiles.getChannel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(FlutterWearTiles.Tag, "onDestroy (FlutterTileService)");

        _channel.destory();
    }
}
