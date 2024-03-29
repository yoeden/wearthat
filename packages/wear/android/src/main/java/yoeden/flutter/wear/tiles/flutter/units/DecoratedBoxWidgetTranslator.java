package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.dp;

import androidx.wear.protolayout.ColorBuilders;
import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterModifierTileWidgetTranslator;

public class DecoratedBoxWidgetTranslator extends FlutterModifierTileWidgetTranslator {
    public final static String TypeId = "__decoratedbox";
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

        if(decoration.contains("padding"))
        {
            final FlutterTileWidgetParcel padding = decoration.getNestedOrThrow("padding");
            builder.setPadding(
                    new ModifiersBuilders.Padding.Builder()
                            .setBottom(dp(padding.getFloat("b")))
                            .setTop(dp(padding.getFloat("t")))
                            .setEnd(dp(padding.getFloat("r")))
                            .setStart(dp(padding.getFloat("l")))
                            .build()
            );
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
