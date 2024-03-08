package yoeden.flutter.wear.base.channel;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public abstract class CallableChannelWrapper extends InvokableChannelWrapper implements MethodChannel.MethodCallHandler {
    private final HashMap<String, IChannelMethodProxy> _map;

    public CallableChannelWrapper(
            Context context,
            MethodChannelFactory factory,
            String name,
            HashMap<String, IChannelMethodProxy> _map) {
        super(context, factory, name);
        this._map = _map;
        _channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (_map.containsKey(call.method)) {
            try {
                Object r = _map.get(call.method).invoke(getApplicationContext(), call.arguments());
                result.success(r);
            } catch (Exception e) {
                result.error("", e.getMessage(), null);
            }
        } else {
            result.notImplemented();
        }
    }
}
