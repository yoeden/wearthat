package yoeden.flutter.wear.SORT.tiles.channel;

import android.util.Log;

import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.SORT.tiles.channel.TilesChannel;
import yoeden.flutter.wear.base.channel.MethodChannelDartExecutorFactory;
import yoeden.flutter.wear.base.engine.FlutterEngineInstance;

public abstract class Tiles {
    public static TilesChannel getChannel()  {
        FlutterEngine engine = FlutterEngineInstance.get();
        DartExecutor executor = getTilesDartExecutor(engine);

        //TODO: Is it redundant to open a MethodChannel everytime if the dart executor is already running ?
        return new TilesChannel(new MethodChannelDartExecutorFactory(executor));
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
