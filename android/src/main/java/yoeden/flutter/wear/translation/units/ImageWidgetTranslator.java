package yoeden.flutter.wear.translation.units;

import static androidx.wear.tiles.DimensionBuilders.expand;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.exceptions.TileTranslationException;
import yoeden.flutter.wear.exceptions.UnsupportedValueException;
import yoeden.flutter.wear.translation.FlutterTileWidgetParcel;
import yoeden.flutter.wear.translation.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.translation.FlutterTileWidgetsTranslator;

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

