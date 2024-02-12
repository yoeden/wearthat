package yoeden.flutter.wear;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.concurrent.futures.CallbackToFutureAdapter;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yoeden.flutter.wear.base.engine.FlutterEngineInstance;
import yoeden.flutter.wear.SORT.tiles.flutter.root.RootLayoutTile;
import yoeden.flutter.wear.SORT.tiles.flutter.root.TileFreshness;
import yoeden.flutter.wear.SORT.tiles.flutter.FlutterTileWidgetParcel;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public abstract class FlutterTilesChannel {
    private static final String CHANNEL = "flutter_wear_tiles";

    public static void throwError(String errorMessage) {
        final MethodChannel channel = getChannel();
        channel.invokeMethod("throwError", errorMessage);
    }

    public static ListenableFuture<List<String>> requestResources(String name) {
        final MethodChannel channel = getChannel();
        return CallbackToFutureAdapter.getFuture(completer -> {
            channel.invokeMethod("requestResources", name, new MethodChannel.Result() {
                @Override
                public void success(@Nullable Object result) {
                    completer.set((List<String>) result);
                }

                @Override
                public void error(@NonNull String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                    //TODO: Find a way to forward this error to provide more information for the developer
                    Log.e(FlutterWearTiles.Tag, "requestResources error: " + errorMessage);
                    completer.setException(new Exception(errorMessage));
                }

                @Override
                public void notImplemented() {
                    Log.d(FlutterWearTiles.Tag, "Channel method not implemented (requestResources).");
                    completer.setException(new Exception("Channel method not implemented (requestResources)."));
                }
            });
            return "requestResources.Async operation";
        });
    }

    public static ListenableFuture<RootLayoutTile> requestLayoutForRoute(String name, String route) {
        final MethodChannel channel = getChannel();
        return CallbackToFutureAdapter.getFuture(completer -> {
            channel.invokeMethod("requestLayoutForRoute", new ArrayList<String>() {
                {
                    add(name);
                    add(route);
                }
            }, new MethodChannel.Result() {
                @Override
                public void success(@Nullable Object result) {
                    HashMap<String, Object> args = (HashMap<String, Object>) result;

                    TileFreshness freshness = TileFreshness.fromJson((Map<String, Object>) args.get("freshness"));
                    FlutterTileWidgetParcel tile = new FlutterTileWidgetParcel((Map<String, Object>) args.get("tile"));

                    //new FlutterTileWidgetParcel()
                    completer.set(new RootLayoutTile(tile, freshness));
                }

                @Override
                public void error(@NonNull String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                    //TODO: Find a way to forward this error to provide more information for the developer
                    Log.e(FlutterWearTiles.Tag, "requestLayoutForRoute error: " + errorMessage);
                    completer.setException(new Exception(errorMessage));
                }

                @Override
                public void notImplemented() {
                    Log.d(FlutterWearTiles.Tag, "Channel method not implemented (requestLayoutForRoute).");
                    completer.setException(new Exception("Channel method not implemented (requestLayoutForRoute)."));
                }
            });
            return "requestLayoutForRoute.Async operation";
        });
    }

    private static MethodChannel getChannel()  {
        FlutterEngine engine = FlutterEngineInstance.get();
        DartExecutor executor = getTilesDartExecutor(engine);

        //TODO: Is it redundant to open a MethodChannel everytime if the dart executor is already running ?
        return new MethodChannel(executor.getBinaryMessenger(), CHANNEL);
    }

    private static DartExecutor getTilesDartExecutor(FlutterEngine engine)
    {
        if (!engine.getDartExecutor().isExecutingDart()) {
            //TODO: Tinker with this a little bit more
            engine
                    .getDartExecutor()
                    .executeDartEntrypoint(new DartExecutor.DartEntrypoint(FlutterInjector.instance().flutterLoader().findAppBundlePath(), "maintile"));
//               .executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
//                    .executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault(), new ArrayList<String>() {
//                        {
//                            add("--get-tiles");
//                        }
//                    });
        } else {
            //TODO: If Dart executor already running than the main of tiles is still alive, any thing to do here ?
            Log.w(FlutterWearTiles.Tag, "Dart executor already running.");
        }

        return engine.getDartExecutor();
    }
}