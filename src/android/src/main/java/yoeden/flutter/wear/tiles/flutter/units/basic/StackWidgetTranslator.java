package yoeden.flutter.wear.tiles.flutter.units.basic;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.dp;
import static androidx.wear.protolayout.DimensionBuilders.expand;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class StackWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__stack";
    public final static String typeName = "Stack";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final List<FlutterTileWidgetParcel> children = widget.getChildren();

        LayoutElementBuilders.Box.Builder builder = new LayoutElementBuilders.Box.Builder()
                //TODO: setHorizontalAlignment
                //TODO: setVerticalAlignment
                //TODO: Check if should fit or wrap
                .setWidth(expand())
                .setHeight(expand());

        if(modifiers != null) builder.setModifiers(modifiers);

        for (FlutterTileWidgetParcel child : children) {
            builder.addContent(translator.translate(child,deviceParameters));
        }

        return builder.build();
    }
}
