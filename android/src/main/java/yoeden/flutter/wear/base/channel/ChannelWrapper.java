package yoeden.flutter.wear.base.channel;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;

import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public abstract class ChannelWrapper extends ContextWrapper implements MethodChannel.MethodCallHandler
{
    private final HashMap<String,IChannelMethodProxy> _map;
    private final MethodChannel _channel;

    protected ChannelWrapper(
            Context context,
            HashMap<String, IChannelMethodProxy> map,
            MethodChannelFactory factory,
            String name) {

        super(context);

        _map = map;
        _channel = factory.create(name);

        _channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if(_map.containsKey(call.method))
        {
            try{
                Object r = _map.get(call.method).invoke(getApplicationContext(),call.arguments());
                result.success(r);
            }catch (Exception e){
                result.error("",e.getMessage(),null);
            }
        }else{
            result.notImplemented();
        }
    }

    public void nofity(String method,Object arg)
    {
        _channel.invokeMethod(method,arg);
    }
}

interface IChannelMethodProxy
{
        <T> Object invoke(Context context, T args);
}
