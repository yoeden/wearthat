package yoeden.flutter.wear.tiles.channels;

import android.content.Context;
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
import yoeden.flutter.wear.base.channel.InvokableChannelWrapper;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.base.channel.MethodChannelFactory;
import yoeden.flutter.wear.tiles.flutter.root.RootLayoutTile;
import yoeden.flutter.wear.tiles.flutter.root.TileFreshness;
import yoeden.flutter.wear.tiles.resources.TileResource;

public class TilesLayoutChannel extends InvokableChannelWrapper {
    public TilesLayoutChannel(Context context, MethodChannelFactory factory) {
        super(context, factory, "wear/tiles");
    }

    public void destory() {
        notify("destroy", null);
    }

    public ListenableFuture<List<TileResource>> requestResources(String tile) {
        return invoke("requestResources", tile, result -> {
            final List<Map<String,Object>> rawResources = (List<Map<String,Object>>)result;
            final List<TileResource> resources = new ArrayList<>(rawResources.size());

            for (Map<String,Object> r: rawResources) {
                resources.add(TileResource.fromJson(r));
            }

            return resources;
        });
    }

    public ListenableFuture<RootLayoutTile> requestLayout(String tile, String route,String state) {
        return invoke("requestLayoutForRoute", new ArrayList<String>() {
            {
                add(tile);
                add(route);
                add(state);
            }
        }, result -> {
            HashMap<String, Object> args = (HashMap<String, Object>) result;

            TileFreshness freshness = TileFreshness.fromJson((Map<String, Object>) args.get("freshness"));
            FlutterTileWidgetParcel widgetTile = new FlutterTileWidgetParcel((Map<String, Object>) args.get("tile"));

            return new RootLayoutTile(widgetTile, freshness);
        });
    }

    public void pushNamed(String route)
    {
        invoke("pushNamed",route);
    }
}
