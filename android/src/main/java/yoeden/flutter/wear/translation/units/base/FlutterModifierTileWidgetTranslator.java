package yoeden.flutter.wear.translation.units.base;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.exceptions.TileTranslationException;
import yoeden.flutter.wear.translation.FlutterTileWidgetParcel;
import yoeden.flutter.wear.translation.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.translation.FlutterTileWidgetsTranslator;

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
