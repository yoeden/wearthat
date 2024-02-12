package yoeden.flutter.wear.SORT.tiles.flutter.units;

import static androidx.wear.tiles.DimensionBuilders.expand;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import java.util.List;

import yoeden.flutter.wear.SORT.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.SORT.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.SORT.tiles.flutter.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.SORT.tiles.flutter.FlutterTileWidgetsTranslator;

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
