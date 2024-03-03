package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.protolayout.DimensionBuilders.dp;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class FractionallySizedBox implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__fractionallySizedBox";
    public final static String typeName = "FractionallySizedBox";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context, ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) {

        LayoutElementBuilders.Spacer.Builder builder = new LayoutElementBuilders.Spacer.Builder();

        //TODO: Check for boundaries, shouldn't be bigger than 1

        if (widget.contains("width")) builder.setWidth(dp(widget.getFloat("width") * deviceParameters.getScreenWidthDp()));
        if (widget.contains("height")) builder.setHeight(dp(widget.getFloat("height")* deviceParameters.getScreenHeightDp()));

        return builder.build();
    }
}