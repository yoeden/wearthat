package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.protolayout.ColorBuilders.argb;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.Button;
import androidx.wear.protolayout.material.ButtonColors;
import androidx.wear.protolayout.material.Colors;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;

public class ButtonWidget implements FlutterTileWidgetTranslator {
    public static final String TypeId = "__button";

    @Override
    public LayoutElementBuilders.LayoutElement translate(FlutterTileWidgetsTranslator translator, Context context, ModifiersBuilders.Modifiers modifiers, FlutterTileWidgetParcel widget, DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final ModifiersBuilders.Clickable clickable = ClickableUtils.buildClickable(widget);
        final Button.Builder builder = new Button.Builder(context, clickable);

        if (widget.contains(CommonTileProperties.Child)) {
            final LayoutElementBuilders.LayoutElement child = translator.translate(context, widget.getChild(), deviceParameters);
            builder.setCustomContent(child);
        }

        if (widget.contains(CommonTileProperties.Style)) {
            final FlutterTileWidgetParcel style = widget.getNested(CommonTileProperties.Style);

            int primary = Colors.DEFAULT.getPrimary();
            int onPrimary = Colors.DEFAULT.getOnPrimary();
            int surface = Colors.DEFAULT.getSurface();
            int onSurface = Colors.DEFAULT.getOnSurface();

            final Colors colors = new Colors(
                    style.getIntOrDefault("backgroundColor", primary),
                    style.getIntOrDefault("foregroundColor", onPrimary),
                    surface,
                    onSurface
            );

            builder.setButtonColors(ButtonColors.primaryButtonColors(colors));
        }

        return builder.build();
    }
}
