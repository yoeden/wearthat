package yoeden.flutter.wear;

import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import yoeden.flutter.wear.communication.WearCommunicationChannel;
import yoeden.flutter.wear.tiles.channels.TilesManagerChannel;
import yoeden.flutter.wear.tiles.preview.FlutterTilePreviewFactory;
import yoeden.flutter.wear.tiles.services.TileUpdater;
import yoeden.flutter.wear.base.channel.factories.MethodChannelFlutterPluginBindingFactory;

public class WearPlugin implements FlutterPlugin {
    private WearCommunicationChannel _communication;
    private TilesManagerChannel _manager;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        //This is called when the application is running
        Log.i(FlutterWearTiles.Tag, "[Plugin Entry] onAttachedToEngine");

        //
        _communication = new WearCommunicationChannel(
                binding.getApplicationContext(),
                new MethodChannelFlutterPluginBindingFactory(binding)
        );

        //
        _manager = new TilesManagerChannel(
                binding.getApplicationContext(),
                new MethodChannelFlutterPluginBindingFactory(binding)
        );

        //
        binding
                .getPlatformViewRegistry()
                .registerViewFactory("TILE_PREVIEW", new FlutterTilePreviewFactory());
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        Log.i(FlutterWearTiles.Tag, "[Plugin Entry] onDetachedFromEngine");

        _manager = null;
        _communication = null;
    }
}
