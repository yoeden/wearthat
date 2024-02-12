package yoeden.flutter.wear.SORT.tiles.channel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.concurrent.futures.CallbackToFutureAdapter;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.SORT.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.base.channel.MethodChannelFactory;
import yoeden.flutter.wear.SORT.tiles.flutter.root.RootLayoutTile;
import yoeden.flutter.wear.SORT.tiles.flutter.root.TileFreshness;

public class TilesChannel  implements MethodChannel.MethodCallHandler{
    private final MethodChannel _channel;

    public TilesChannel(MethodChannelFactory factory) {
        _channel = factory.create("wear/tiles");
        _channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        result.error("XX","'wear/tiles' is a unidirectional method channel, dont send messages here !",null);
    }

    public void destory()
    {
        _channel.invokeMethod("destroy", null, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object result) {
                Log.i(FlutterWearTiles.Tag,"Success for destroy");
            }

            @Override
            public void error(@NonNull String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                Log.e(FlutterWearTiles.Tag, "destroy error: " + errorMessage);
            }

            @Override
            public void notImplemented() {
                Log.e(FlutterWearTiles.Tag, "Channel method not implemented (destroy).");
            }
        });
    }

    public List<String> requestResources(String tile) throws Exception {
        ListenableFuture<List<String>> future =  CallbackToFutureAdapter.getFuture(completer -> {
            _channel.invokeMethod("requestResources", tile, new MethodChannel.Result() {
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
                    Log.e(FlutterWearTiles.Tag, "Channel method not implemented (requestResources).");
                    completer.setException(new Exception("Channel method not implemented (requestResources)."));
                }
            });
            return "requestResources.Async operation";
        });

        return future.get();
    }

    public ListenableFuture<RootLayoutTile> requestLayout(String tile, String route) throws Exception {
        ListenableFuture<RootLayoutTile> future = CallbackToFutureAdapter.getFuture(completer -> {
            _channel.invokeMethod("requestLayoutForRoute", new ArrayList<String>() {
                {
                    add(tile);
                    add(route);
                }
            }, new MethodChannel.Result() {
                @Override
                public void success(@Nullable Object result) {
                    Log.i(FlutterWearTiles.Tag,"Success for requestLayoutForRoute");
                    HashMap<String, Object> args = (HashMap<String, Object>) result;

                    TileFreshness freshness = TileFreshness.fromJson((Map<String, Object>) args.get("freshness"));
                    FlutterTileWidgetParcel tile = new FlutterTileWidgetParcel((Map<String, Object>) args.get("tile"));

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
                    Log.e(FlutterWearTiles.Tag, "Channel method not implemented (requestLayoutForRoute).");
                    completer.setException(new Exception("Channel method not implemented (requestLayoutForRoute)."));
                }
            });
            return "requestLayoutForRoute.Async operation";
        });

        return future;
    }

    public ListenableFuture<String> test() {
        return CallbackToFutureAdapter.getFuture(completer -> {
            _channel.invokeMethod("test", null, new MethodChannel.Result() {
                @Override
                public void success(@Nullable Object result) {
                    completer.set(result.toString());
                }

                @Override
                public void error(@NonNull String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                    completer.setException(new Exception(errorMessage));
                }

                @Override
                public void notImplemented() {
                    completer.setException(new Exception("Channel method not implemented (requestLayoutForRoute)."));
                }
            });

            return "";
        });
    }
}
