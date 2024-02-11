package yoeden.flutter.wear.channel;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;

public class MethodChannelFlutterPluginBindingFactory implements MethodChannelFactory {
    final FlutterPlugin.FlutterPluginBinding binding;

    public MethodChannelFlutterPluginBindingFactory(FlutterPlugin.FlutterPluginBinding binding) {
        this.binding = binding;
    }

    @Override
    public MethodChannel create(String channel) {
        return new MethodChannel(binding.getBinaryMessenger(), channel);
    }
}
