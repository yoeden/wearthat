package yoeden.flutter.wear.tiles.flutter.units.basic;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.expand;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class ImageWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__image";
    public final static String typeName = "Image";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context, ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final LayoutElementBuilders.Image.Builder builder = new LayoutElementBuilders.Image.Builder()
                .setWidth(expand())
                .setHeight(expand())
                .setContentScaleMode(widget.getIntOrThrow("fit"));

        //TODO:
        //builder.setColorFilter(new LayoutElementBuilders.ColorFilter.Builder().setTint())

        final String resource = widget.getStringOrThrow("resource");
        builder.setResourceId(resource);

        //From docs: Note that only Android image resources can be tinted; Inline images will not be tinted, and this property will have no effect.
        //builder.setColorFilter(new LayoutElementBuilders.ColorFilter.Builder().setTint(argb(0xffff0000)).build());

        if (modifiers != null) builder.setModifiers(modifiers);

        return builder.build();
    }
}

