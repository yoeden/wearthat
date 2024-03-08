package yoeden.flutter.wear.tiles.flutter.units.layouts;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.layouts.MultiButtonLayout;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;

public class MultiButtonLayoutWidget implements FlutterTileWidgetTranslator {
    public static final String TypeId = "__multibutton_layout";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final MultiButtonLayout.Builder builder = new MultiButtonLayout.Builder();

        final List<FlutterTileWidgetParcel> children = widget.getChildren();
        for (FlutterTileWidgetParcel child : children) {
            final LayoutElementBuilders.LayoutElement childWidget = translator.translate(context,child, deviceParameters);
            builder.addButtonContent(childWidget);
        }

        return builder.build();
    }
}
