package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.tiles.ColorBuilders.argb;
import static androidx.wear.tiles.DimensionBuilders.degrees;
import static androidx.wear.tiles.DimensionBuilders.dp;
import static androidx.wear.tiles.LayoutElementBuilders.VERTICAL_ALIGN_CENTER;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class ArcWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__arc";
    public final static String typeName = "Arc";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        LayoutElementBuilders.ArcLine.Builder builder = new LayoutElementBuilders.ArcLine.Builder();

        builder.setLength(degrees(widget.getFloatOrThrow("value")));

        if (widget.contains("style")) {
            FlutterTileWidgetParcel style = widget.getNested("style");

            if (style.contains("color")) builder.setColor(argb(style.getInt("color")));
            if (style.contains("thickness")) builder.setThickness(dp(style.getFloat("thickness")));
        }

        //if (args.containsKey("anchorAngle") && args.get("anchorAngle") != null)

        return new LayoutElementBuilders.Arc.Builder()
                .addContent(builder.build())
                .addContent(new LayoutElementBuilders.ArcSpacer.Builder().setLength(degrees(180)).build())
                .addContent(new LayoutElementBuilders.ArcText.Builder().setText(new SimpleDateFormat("HH:mm:ss").format(new Date())).build())
                //Element starts at 12 o'clock or 0 degree position in the circle
                .setAnchorAngle(degrees(0))
                .setAnchorType(LayoutElementBuilders.ARC_ANCHOR_START)
                .setVerticalAlign(VERTICAL_ALIGN_CENTER)
                .build();
    }
}
