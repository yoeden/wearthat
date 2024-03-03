package yoeden.flutter.wear.tiles.flutter.units.layouts;

import static androidx.wear.protolayout.DimensionBuilders.expand;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class StackWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__stack";
    public final static String typeName = "Stack";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context, ModifiersBuilders.Modifiers modifiers,
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
            builder.addContent(translator.translate(context,child,deviceParameters));
        }

        return builder.build();
    }
}
