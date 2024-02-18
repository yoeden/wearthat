package yoeden.flutter.wear.tiles.flutter.units.basic;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.dp;
import static androidx.wear.protolayout.DimensionBuilders.expand;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.exceptions.UnsupportedValueException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class ImageWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__image";
    public final static String typeName = "Image";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final LayoutElementBuilders.Image.Builder builder = new LayoutElementBuilders.Image.Builder()
                .setWidth(expand())
                .setHeight(expand())
                .setContentScaleMode(widget.getIntOrThrow("fit"));

        final FlutterTileWidgetParcel provider = widget.getNestedOrThrow("provider");
        final String type = provider.getStringOrThrow("type");

        if (type.equals("asset")) {
            builder.setResourceId(provider.getStringOrThrow("path"));
        } else {
            throw new UnsupportedValueException("type", type);
        }

        if (modifiers != null) builder.setModifiers(modifiers);

        return builder.build();
    }
}

