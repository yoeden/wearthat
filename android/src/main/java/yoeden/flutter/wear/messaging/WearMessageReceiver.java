package yoeden.flutter.wear.messaging;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

import yoeden.flutter.wear.FlutterWearTiles;

public class WearMessageReceiver implements MessageClient.OnMessageReceivedListener {
    private final MethodChannel _methodChannel;

    public WearMessageReceiver(MethodChannel methodChannel) {
        _methodChannel = methodChannel;
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        byte[] bytes = messageEvent.getData();
        Log.i(messageEvent.getPath(), new String(bytes));

        Map<String,Object> json = new HashMap<>();
        json.put("path",messageEvent.getPath());
        json.put("data",messageEvent.getData());
        json.put("id",messageEvent.getRequestId());
        json.put("node",messageEvent.getSourceNodeId());

        Log.d(FlutterWearTiles.Tag,"Wear message received");

        _methodChannel.invokeMethod("data_received",json);
    }
}
