package yoeden.flutter.wear.base.channel;

import android.content.Context;

public interface IChannelMethodProxy {
    Object invoke(Context context, Object args);
}
