package yoeden.flutter.wear.tiles.services;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yoeden.flutter.wear.FlutterWearTiles;

public abstract class KnownTiles {

    private static final HashMap<String, Class<?>> tiles = new HashMap<>();

    private KnownTiles() {

    }

    public static void init(Context context) {
        if (!tiles.isEmpty()) return;

        try {
            final int flags = PackageManager.GET_CONFIGURATIONS | PackageManager.GET_INTENT_FILTERS | PackageManager.GET_SERVICES;
            final PackageManager pm = context.getPackageManager();
            final PackageInfo pkg = pm.getPackageInfo(context.getPackageName(), flags);

            for (ServiceInfo service : pkg.services) {
                if (service.permission != null && service.permission.equals("com.google.android.wearable.permission.BIND_TILE_PROVIDER")) {
                    final Class<?> clazz = Class.forName(service.name);
                    Log.i(FlutterWearTiles.Tag, "Found tile: " + clazz.getSimpleName());
                    tiles.put(clazz.getSimpleName(), clazz);
                }
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getTile(String name) {
        return tiles.getOrDefault(name, null);
    }
}
