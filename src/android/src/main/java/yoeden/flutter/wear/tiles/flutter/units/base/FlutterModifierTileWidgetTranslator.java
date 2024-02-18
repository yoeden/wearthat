package yoeden.flutter.wear.tiles.flutter.units.base;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.dp;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public abstract class FlutterModifierTileWidgetTranslator implements FlutterTileWidgetTranslator {
    @Override
    public LayoutElementBuilders.LayoutElement translate(FlutterTileWidgetsTranslator translator,
                                                         ModifiersBuilders.Modifiers modifiers,
                                                         FlutterTileWidgetParcel widget,
                                                         DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final ModifiersBuilders.Modifiers.Builder builder = new ModifiersBuilders.Modifiers.Builder();
        tryAppendModifiers(modifiers, builder);
        translate(builder,widget,deviceParameters);

        return translator.translate(widget.getChildOrThrow(), builder.build(),deviceParameters);
    }

    protected abstract void translate(
            ModifiersBuilders.Modifiers.Builder builder,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException;

    private void tryAppendModifiers(ModifiersBuilders.Modifiers modifiers, ModifiersBuilders.Modifiers.Builder builder) {
        if (modifiers != null) {
            if (modifiers.getBackground() != null) builder.setBackground(modifiers.getBackground());
            if (modifiers.getBorder() != null) builder.setBorder(modifiers.getBorder());
            if (modifiers.getClickable() != null) builder.setClickable(modifiers.getClickable());
            if (modifiers.getPadding() != null) builder.setPadding(modifiers.getPadding());
        }
    }
}
