package yoeden.flutter.wear.translation.units;

import static androidx.wear.tiles.ColorBuilders.argb;
import static androidx.wear.tiles.DimensionBuilders.dp;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.exceptions.TileTranslationException;
import yoeden.flutter.wear.translation.FlutterTileWidgetParcel;
import yoeden.flutter.wear.translation.units.base.FlutterModifierTileWidgetTranslator;

public class DecoratedBoxWidgetTranslator extends FlutterModifierTileWidgetTranslator {
    public final static String typeId = "__decoratedbox";
    public final static String typeName = "DecoratedBox";

    @Override
    protected void translate(
            ModifiersBuilders.Modifiers.Builder builder,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final FlutterTileWidgetParcel decoration = widget.getNestedOrThrow("decoration");

        if (decoration.contains("color")) {
            final ModifiersBuilders.Background.Builder backgroundBuilder = new ModifiersBuilders.Background.Builder();
            backgroundBuilder.setColor(argb(decoration.getInt("color")));

            if (decoration.contains("borderRadius"))
                backgroundBuilder.setCorner(new ModifiersBuilders.Corner.Builder().setRadius(dp(decoration.getFloat("borderRadius"))).build());

            builder.setBackground(backgroundBuilder.build());
        }

        if (decoration.contains("border")) {
            final FlutterTileWidgetParcel border = decoration.getNested("border");
            final ModifiersBuilders.Border.Builder borderBuilder = new ModifiersBuilders.Border.Builder();

            if (border.contains("color")) borderBuilder.setColor(argb(border.getInt("color")));
            if (border.contains("width")) borderBuilder.setWidth(dp(border.getFloat("width")));

            builder.setBorder(borderBuilder.build());
        }
    }
}
