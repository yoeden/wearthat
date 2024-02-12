package yoeden.flutter.wear.base.channel;

import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public class MethodChannelDartExecutorFactory implements MethodChannelFactory {

    private final DartExecutor _executor;

    public MethodChannelDartExecutorFactory(DartExecutor executor) {
        _executor = executor;
    }

    @Override
    public MethodChannel create(String channel) {
        return new MethodChannel(_executor.getBinaryMessenger(),channel);
    }
}
