package yoeden.flutter.wear.tiles.channels;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.base.channel.CallableChannelWrapper;
import yoeden.flutter.wear.base.channel.InvokableChannelWrapper;
import yoeden.flutter.wear.base.channel.IChannelMethodProxy;
import yoeden.flutter.wear.base.channel.MethodChannelFactory;
import yoeden.flutter.wear.tiles.services.TileUpdater;

public class TilesManagerChannel extends CallableChannelWrapper {

    private static final HashMap<String, IChannelMethodProxy> _methods = new HashMap<>();

    static {
        _methods.put("update", TilesManagerChannel::updateTile);
    }

    public TilesManagerChannel(Context context, MethodChannelFactory factory) {
        super(context, factory, "wear/manager", _methods);
    }

    private static Object updateTile(Context context, Object args) {
        final String name = (String) args;
        new TileUpdater(context).update();
        Log.i(FlutterWearTiles.Tag, "updateTile: " + name);
        return null;
    }
}
