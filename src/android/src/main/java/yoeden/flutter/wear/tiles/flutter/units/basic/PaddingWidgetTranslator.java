package yoeden.flutter.wear.tiles.flutter.units.basic;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.dp;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;


import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterModifierTileWidgetTranslator;

public class PaddingWidgetTranslator extends FlutterModifierTileWidgetTranslator {
    public final static String typeId = "__padding";
    public final static String typeName = "Padding";

    @Override
    protected void translate(
            ModifiersBuilders.Modifiers.Builder builder,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final FlutterTileWidgetParcel padding = widget.getNestedOrThrow("padding");

                builder.setPadding(
                        new ModifiersBuilders.Padding.Builder()
                                .setBottom(dp(padding.getFloat("b")))
                                .setTop(dp(padding.getFloat("t")))
                                .setEnd(dp(padding.getFloat("r")))
                                .setStart(dp(padding.getFloat("l")))
                                .build()
                );

    }
}

