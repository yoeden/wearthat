package yoeden.flutter.wear.base.engine;

import android.content.Context;

import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.tiles.channels.TilesLayoutChannel;
import yoeden.flutter.wear.base.channel.factories.MethodChannelDartExecutorFactory;

public class ScopedFlutterEngineWithTileEntrypoint {
    private FlutterEngine engine;
    private DartExecutor dartExecutor;

    public ScopedFlutterEngineWithTileEntrypoint() {
    }

    public void initialize(Context context) {
        if (!FlutterInjector.instance().flutterLoader().initialized()) {
            FlutterInjector.instance().flutterLoader().startInitialization(context);
            FlutterInjector.instance().flutterLoader().ensureInitializationComplete(context, new String[0]);
        }

        engine = new FlutterEngine(context);
    }

    public TilesLayoutChannel getChannel(Context context) {
        return new TilesLayoutChannel(context, new MethodChannelDartExecutorFactory(getDartExecutor()));
    }

    public DartExecutor getDartExecutor() {
        if (dartExecutor == null || !dartExecutor.isExecutingDart()) {
            final DartExecutor.DartEntrypoint entrypoint = new DartExecutor.DartEntrypoint(FlutterInjector.instance().flutterLoader().findAppBundlePath(), "maintile");
            engine
                    .getDartExecutor()
                    .executeDartEntrypoint(entrypoint);
            dartExecutor = engine.getDartExecutor();

            Log.i(FlutterWearTiles.Tag, FlutterInjector.instance().flutterLoader().findAppBundlePath());
        }

        return dartExecutor;
    }

    public void destroy() {
        engine.destroy();
    }
}
