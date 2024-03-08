package yoeden.flutter.wear.base.channel;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.concurrent.futures.CallbackToFutureAdapter;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import yoeden.flutter.wear.FlutterWearTiles;

public abstract class InvokableChannelWrapper extends ContextWrapper {
    protected final MethodChannel _channel;

    protected InvokableChannelWrapper(
            Context context,
            MethodChannelFactory factory,
            String name) {
        super(context);
        _channel = factory.create(name);
    }

    protected void notify(String method, Object args) {
        _channel.invokeMethod(method, args, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object result) {
                Log.d(FlutterWearTiles.Tag, method + " success !");
            }

            @Override
            public void error(@NonNull String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                Log.e(FlutterWearTiles.Tag, method + " error: " + errorMessage);
            }

            @Override
            public void notImplemented() {
                Log.e(FlutterWearTiles.Tag, method + " not implemented !");
            }
        });
    }

    protected void invoke(String method, Object args) {
        _channel.invokeMethod(method, args);
    }

    protected <T> ListenableFuture<T> invoke(String method, Object args, Transformer<T> transformer) {
        return CallbackToFutureAdapter.getFuture(completer -> {
            _channel.invokeMethod(method, args,
                    new MethodChannel.Result() {
                        @Override
                        public void success(@Nullable Object result) {
                            completer.set(transformer.transform(result));
                        }

                        @Override
                        public void error(@NonNull String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                            //TODO: Find a way to forward this error to provide more information for the developer
                            Log.e(FlutterWearTiles.Tag, method + " error: " + errorMessage);
                            completer.setException(new Exception(errorMessage));
                        }

                        @Override
                        public void notImplemented() {
                            Log.e(FlutterWearTiles.Tag, "Channel method not implemented (requestLayoutForRoute).");
                            completer.setException(new Exception("Channel method not implemented (requestLayoutForRoute)."));
                        }
                    });
            return "Async operation";
        });
    }
}

