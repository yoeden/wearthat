package yoeden.flutter.wear.tiles.services;

import androidx.annotation.NonNull;
import androidx.wear.tiles.RequestBuilders;
import androidx.wear.tiles.ResourceBuilders;
import androidx.wear.tiles.TileBuilders;
import androidx.wear.tiles.TileService;

import com.google.common.util.concurrent.ListenableFuture;

import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.tiles.channels.TilesLayoutChannel;
import yoeden.flutter.wear.base.engine.ScopedFlutterEngineWithTileEntrypoint;

public class FlutterTileService extends TileService {

    private ScopedFlutterEngineWithTileEntrypoint _engine;
    private TilesLayoutChannel _channel;

    @NonNull
    @Override
    protected ListenableFuture<TileBuilders.Tile> onTileRequest(@NonNull RequestBuilders.TileRequest requestParams) {
        final String route = requestParams.getState().getLastClickableId() == null || requestParams.getState().getLastClickableId().equals("")
                ? FlutterWearTiles.RootRoute :
                requestParams.getState().getLastClickableId();

        return FlutterTileLayout.onTileRequest(this, _channel, requestParams, route);
    }

    @NonNull
    @Override
    protected ListenableFuture<ResourceBuilders.Resources> onResourcesRequest(@NonNull RequestBuilders.ResourcesRequest p) {
        return FlutterTileResources.onResourcesRequest(this, _channel, p);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _engine = new ScopedFlutterEngineWithTileEntrypoint();
        _engine.initialize(this);
        _channel = _engine.getChannel(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _channel.destory();
        _engine.destroy();
    }
}
