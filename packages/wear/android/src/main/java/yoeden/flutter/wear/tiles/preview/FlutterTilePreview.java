package yoeden.flutter.wear.tiles.preview;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import yoeden.flutter.wear.tiles.services.FlutterTileService;
import io.flutter.plugin.platform.PlatformView;

import java.util.Map;

import androidx.wear.tiles.manager.TileUiClient;

import android.content.ComponentName;
import android.widget.FrameLayout;


/**
 * This class helps to preview the tile inside the flutter app
 */
public class FlutterTilePreview implements PlatformView {
    @NonNull private final FrameLayout f;
    @NonNull private final TileUiClient tileClient;

    FlutterTilePreview(@NonNull Context context, int id, @NonNull Class<?> tileClass) {
        f = new FrameLayout(context);

        tileClient = new TileUiClient(context, new ComponentName(context, tileClass), f);
        tileClient.connect();

    }

    @NonNull
    @Override
    public View getView() {
        return f;
    }

    @Override
    public void dispose() {
        tileClient.close();
    }
}

