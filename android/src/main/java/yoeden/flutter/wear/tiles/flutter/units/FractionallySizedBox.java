package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.tiles.DimensionBuilders.dp;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class FractionallySizedBox implements FlutterTileWidgetTranslator {
    public final static String typeId = "__fractionallySizedBox";
    public final static String typeName = "FractionallySizedBox";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) {

        LayoutElementBuilders.Spacer.Builder builder = new LayoutElementBuilders.Spacer.Builder();

        //TODO: Check for boundaries, shouldn't be bigger than 1

        if (widget.contains("width")) builder.setWidth(dp(widget.getFloat("width") * deviceParameters.getScreenWidthDp()));
        if (widget.contains("height")) builder.setHeight(dp(widget.getFloat("height")* deviceParameters.getScreenHeightDp()));

        return builder.build();
    }
}