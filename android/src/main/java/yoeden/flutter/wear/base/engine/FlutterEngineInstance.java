package yoeden.flutter.wear.base.engine;

import android.content.Context;
import android.util.Log;

import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import yoeden.flutter.wear.FlutterWearTiles;

public abstract class FlutterEngineInstance {
    private static final String ENGINE_TAG = "wear";

    public static void initialize(Context context) {
        Log.d(FlutterWearTiles.Tag, "Initialize tile channel");
        if (!FlutterInjector.instance().flutterLoader().initialized()) {
            FlutterInjector.instance().flutterLoader().startInitialization(context);
            FlutterInjector.instance().flutterLoader().ensureInitializationComplete(context, new String[0]);
        }

        if (!FlutterEngineCache.getInstance().contains(ENGINE_TAG)) {
            FlutterEngineCache.getInstance().put(ENGINE_TAG, new FlutterEngine(context));
        }
    }

    public static void destroy() {
        Log.d(FlutterWearTiles.Tag, "Destroy tile channel");
        if (FlutterEngineCache.getInstance().contains(ENGINE_TAG)) {
            FlutterEngine flutterEngine = FlutterEngineCache.getInstance().get(ENGINE_TAG);
            FlutterEngineCache.getInstance().remove(ENGINE_TAG);

            if(flutterEngine != null) flutterEngine.destroy();
        }
    }

    public static FlutterEngine get()  {
        FlutterEngine engine = FlutterEngineCache.getInstance().get(ENGINE_TAG);
        return engine;
    }
}
