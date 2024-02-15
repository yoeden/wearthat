package yoeden.flutter.wear.base.channel;

import io.flutter.plugin.common.MethodChannel;

public interface MethodChannelFactory {
    MethodChannel create(String channel);
}

