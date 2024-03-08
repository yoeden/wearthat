package yoeden.flutter.wear.tiles.flutter.units;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.CircularProgressIndicator;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;

public class CircularProgressIndicatorWidget implements FlutterTileWidgetTranslator {
    public static final String TypeId = "__circularprogressindicator";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final CircularProgressIndicator.Builder builder = new CircularProgressIndicator.Builder();

        if(widget.contains("progress")) builder.setProgress(widget.getFloat("progress"));
        if(widget.contains("thickness")) builder.setStrokeWidth(widget.getFloat("thickness"));
        if(widget.contains("startAngle")) builder.setStartAngle(widget.getFloat("startAngle"));
        if(widget.contains("endAngle")) builder.setEndAngle(widget.getFloat("endAngle"));

        builder.setContentDescription("KAKA");

        return builder.build();
    }
}
