package yoeden.flutter.wear;

import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import yoeden.flutter.wear.communication.WearCommunicationChannel;
import yoeden.flutter.wear.SORT.tiles.preview.FlutterTilePreviewFactory;
import yoeden.flutter.wear.SORT.tiles.services.TileUpdater;
import yoeden.flutter.wear.base.channel.MethodChannelFlutterPluginBindingFactory;

public class FlutterWearPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel _channel;
    private TileUpdater _tileUpdater;
    private WearCommunicationChannel _communication;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        //This is called when the application is running
        Log.i(FlutterWearTiles.Tag, "[Plugin] onAttachedToEngine");

        _tileUpdater = new TileUpdater(binding.getApplicationContext());
        _tileUpdater.update();

        _communication = new WearCommunicationChannel(new MethodChannelFlutterPluginBindingFactory(binding),binding.getApplicationContext());

        _channel = new MethodChannel(
                binding.getBinaryMessenger(),
                "flutter_wear_tiles"
        );
        _channel.setMethodCallHandler(this);

        binding
                .getPlatformViewRegistry()
                .registerViewFactory("TILE_PREVIEW", new FlutterTilePreviewFactory());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Log.i(FlutterWearTiles.Tag, "[Plugin] Received method call from flutter: " + call.method);
        switch (call.method) {
            case "requestUpdate":
                Log.i(FlutterWearTiles.Tag,"Request tile update for: "+call.arguments());
                _tileUpdater.update();
                result.success(null);
                break;
            case "logd":
                Log.d(FlutterWearTiles.Tag,call.arguments());
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        Log.i(FlutterWearTiles.Tag, "onDetachedFromEngine: ");
    }
}
