package yoeden.flutter.wear.tiles.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.wear.protolayout.ResourceBuilders;
import androidx.wear.protolayout.StateBuilders;
import androidx.wear.protolayout.TimelineBuilders.Timeline;
import androidx.wear.protolayout.material.Text;
import androidx.wear.tiles.RequestBuilders;
import androidx.wear.tiles.TileBuilders;
import androidx.wear.tiles.TileService;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.tiles.channels.TilesLayoutChannel;
import yoeden.flutter.wear.base.engine.ScopedFlutterEngineWithTileEntrypoint;
import yoeden.flutter.wear.tiles.flutter.root.TileFreshness;

public abstract class FlutterTileService extends TileService {
    private final String name;
    private ScopedFlutterEngineWithTileEntrypoint _engine;
    private TilesLayoutChannel _channel;

    public FlutterTileService(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    protected ListenableFuture<TileBuilders.Tile> onTileRequest(@NonNull RequestBuilders.TileRequest requestParams) {
        final StateBuilders.State state = requestParams.getCurrentState();
        final String route = state.getLastClickableId() == null || state.getLastClickableId().equals("")
                ? FlutterWearTiles.RootRoute :
                state.getLastClickableId();

        return FlutterTileLayout.onTileRequest(
                this,
                state,
                _channel,
                requestParams,
                name,
                route
        );
    }

    @NonNull
    @Override
    protected ListenableFuture<ResourceBuilders.Resources> onTileResourcesRequest(@NonNull RequestBuilders.ResourcesRequest p) {
        return FlutterTileResources.onResourcesRequest(
                this,
                _channel,
                p,
                name
        );
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
