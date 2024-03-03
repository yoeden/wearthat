package yoeden.flutter.wear.tiles.preview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import yoeden.flutter.wear.tiles.services.KnownTiles;

public class FlutterTilePreviewFactory extends PlatformViewFactory {

    public FlutterTilePreviewFactory() {
        super(StandardMessageCodec.INSTANCE);
    }

    @NonNull
    @Override
    public PlatformView create(@NonNull Context context, int id, @Nullable Object args) {
        final Map<String, Object> creationParams = (Map<String, Object>) args;
        final String tile = (String) creationParams.get("tile");

        KnownTiles.init(context);
        final Class<?> tileClass = KnownTiles.getTile(tile);
        if (tileClass == null) throw new RuntimeException(String.format("'%s' wasn't found, are you sure that all tiles are synced ?", tile));

        return new FlutterTilePreview(context, id, tileClass);
    }
}
