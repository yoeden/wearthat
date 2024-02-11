package yoeden.flutter.wear.translation.units;

import static androidx.wear.tiles.DimensionBuilders.dp;
import static androidx.wear.tiles.DimensionBuilders.expand;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.exceptions.TileTranslationException;
import yoeden.flutter.wear.translation.FlutterTileWidgetParcel;
import yoeden.flutter.wear.translation.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.translation.FlutterTileWidgetsTranslator;

public class SizedBoxWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__sizedbox";
    public final static String typeName = "SizedBox";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        //Quirky hack to support child in a SizedBox like flutter
        if (widget.contains("child")) {
            LayoutElementBuilders.Box.Builder builder = new LayoutElementBuilders.Box.Builder()
                    .setWidth(widget.contains("width") ? dp(widget.getFloat("width")) : expand())
                    .setHeight(widget.contains("height") ? dp(widget.getFloat("height")) : expand())
                    .addContent(translator.translate(widget.getNestedOrThrow("child"), deviceParameters))
                    .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
                    .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER);

            if (modifiers != null) builder.setModifiers(modifiers);

            return builder.build();
        } else {
            LayoutElementBuilders.Spacer.Builder builder = new LayoutElementBuilders.Spacer.Builder();

            if (widget.contains("width")) builder.setWidth(dp(widget.getFloat("width")));
            if (widget.contains("height")) builder.setHeight(dp(widget.getFloat("height")));

            if (modifiers != null) builder.setModifiers(modifiers);

            return builder.build();
        }
    }
}
