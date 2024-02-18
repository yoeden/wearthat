package yoeden.flutter.wear.tiles.services;


import static androidx.wear.protolayout.DimensionBuilders.dp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.TimelineBuilders;
import androidx.wear.tiles.RequestBuilders;
import androidx.wear.tiles.TileBuilders;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.List;

import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.tiles.channels.TilesLayoutChannel;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.root.RootLayoutTile;
import yoeden.flutter.wear.tiles.flutter.root.TileFreshness;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class FlutterTileLayout {
    public static ListenableFuture<TileBuilders.Tile> onTileRequest(
            @NonNull Context context,
            @NonNull TilesLayoutChannel _channel,
            @NonNull RequestBuilders.TileRequest request,
            @NonNull String tile,
            @NonNull String route) {

        ListenableFuture<TileBuilders.Tile> result = Futures.immediateFuture(buildTile(buildErrorLayout(), TileFreshness.never()));
        try {
            //TODO: Timeout
            ListenableFuture<RootLayoutTile> treeListenableFuture = JdkFutureAdapters.listenInPoolThread(
                    //TODO: Future support for multiple tile definitions
                    _channel.requestLayout(tile, route)
            );
            result = Futures.transform(
                    treeListenableFuture,
                    root -> {
                        return apply(root, request.getDeviceConfiguration());
                    },
                    context.getMainExecutor()
            );

        } catch (Exception ex) {
            Log.e(FlutterWearTiles.Tag, "Failed building tile: " + ex.getMessage());
        }

        return result;
    }

    private static LayoutElementBuilders.LayoutElement buildErrorLayout() {
        return new LayoutElementBuilders.Column.Builder()
                .addContent(
                        new LayoutElementBuilders.Image.Builder()
                                .setResourceId("__tile_error_image")
                                .setHeight(dp(32))
                                .setWidth(dp(32))
                                .build()
                )
                .addContent(new LayoutElementBuilders.Text.Builder()
                        .setText("Something went wrong")
                        .build()
                )
                .build();
    }

    private static TileBuilders.Tile apply(RootLayoutTile root,
                                           DeviceParametersBuilders.DeviceParameters deviceParameters) {
        LayoutElementBuilders.LayoutElement layout = null;
        if (root == null || root.getTile() == null) {
            Log.e(FlutterWearTiles.Tag, "No tile tree received");
        } else {
            try {
                layout = FlutterTileWidgetsTranslator
                        .getInstance()
                        .translate(root.getTile(), deviceParameters);
            } catch (TileTranslationException e) {
                Log.e(FlutterWearTiles.Tag, e.getMessage());
                e.printStackTrace();
            }
        }

        return buildTile(layout, root.getFreshness());
    }

    private static TileBuilders.Tile buildTile(LayoutElementBuilders.LayoutElement layout, TileFreshness freshness) {
        TimelineBuilders.TimelineEntry.Builder timelineEntryBuilder = new TimelineBuilders.TimelineEntry.Builder()
                .setLayout(new LayoutElementBuilders.Layout.Builder()
                        .setRoot(layout)
                        .build()
                );

        if (freshness.getUntil() != null) {
            timelineEntryBuilder.setValidity(new TimelineBuilders.TimeInterval.Builder().setEndMillis(freshness.getUntil()).build());
        }

        return new TileBuilders.Tile.Builder()
                .setResourcesVersion(FlutterWearTiles.ResourceVersion)
                .setTileTimeline(new TimelineBuilders.Timeline.Builder().addTimelineEntry(timelineEntryBuilder.build()).build())
                .setFreshnessIntervalMillis(freshness.getInterval())
                .build();

    }

}
