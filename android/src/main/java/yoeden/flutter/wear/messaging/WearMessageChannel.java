package yoeden.flutter.wear.messaging;

import android.content.Context;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.Wearable;

import yoeden.flutter.wear.channel.MethodChannelFactory;
import io.flutter.plugin.common.MethodChannel;

public class WearMessageChannel {
    public static final String CHANNEL_NAME = "flutter_wear_tiles_datacallback";

    public static WearMessageChannel create(MethodChannelFactory factory, Context context) {
        return new WearMessageChannel(factory.create(CHANNEL_NAME), context);
    }

    private final MethodChannel _channel;
    private WearMessageReceiver _receiver;
    private MessageClient _client;
    private Context _context;

    private WearMessageChannel(MethodChannel channel, Context context) {
        _channel = channel;
        _context = context;

        _receiver = new WearMessageReceiver(channel);

        _client = Wearable.getMessageClient(context);
        _client.addListener(_receiver);
    }

    public void post(WearMessage message) {
        WearMessageSender.post(message, _context);
    }

    public void destroy() {
        _client.removeListener(_receiver);
        _client = null;
        _receiver = null;
    }
}
