package yoeden.flutter.wear.communication;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.communication.dtos.WearMessage;
import yoeden.flutter.wear.base.channel.MethodChannelFactory;
import yoeden.flutter.wear.utils.TaskHelper;

public class WearCommunicationChannel extends ContextWrapper implements MethodChannel.MethodCallHandler, MessageClient.OnMessageReceivedListener {
    private final MethodChannel _channel;
    private final MessageClient _client;

    public WearCommunicationChannel(MethodChannelFactory factory, Context context) {
        super(context);
        Log.i(FlutterWearTiles.Tag, "WearCommunicationChannel created");
        _channel = factory.create("wear/communication");
        _channel.setMethodCallHandler(this);

        _client = Wearable.getMessageClient(context);
        _client.addListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Log.i(FlutterWearTiles.Tag, "[Plugin] Received method call from flutter: " + call.method);
        switch (call.method) {
            case "send":
                WearMessage message = new Gson().fromJson((String) call.arguments(), WearMessage.class);
                if (message.getNode() == null)
                    TaskHelper.run(() -> WearCommunicationHelper.sendAll(message, getApplicationContext()), result);
                else
                    TaskHelper.run(() -> WearCommunicationHelper.send(message, getApplicationContext()), result);
                break;
            case "getNodes":
                TaskHelper.runResultAsJson(() -> WearCommunicationHelper.getNodes(getApplicationContext()), result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent msg) {
        byte[] bytes = msg.getData();

        Map<String, Object> json = new HashMap<>();
        json.put("path", msg.getPath());
        json.put("data", bytes);
        json.put("id", msg.getRequestId());
        json.put("node", msg.getSourceNodeId());

        Log.d(
                FlutterWearTiles.Tag,
                String.format(
                        "Wear message received (Id: %d, Node: %s, Path: %s, Len: %d)",
                        msg.getRequestId(),
                        msg.getSourceNodeId(),
                        msg.getPath(),
                        bytes.length)
        );

        _channel.invokeMethod("onMessageReceived", json);
    }
}