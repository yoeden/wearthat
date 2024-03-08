package yoeden.flutter.wear.tiles.flutter.units.basic;

import static androidx.wear.protolayout.DimensionBuilders.dp;
import static androidx.wear.protolayout.DimensionBuilders.expand;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class SizedBoxWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__sizedbox";
    public final static String typeName = "SizedBox";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context, ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        //Quirky hack to support child in a SizedBox like flutter
        if (widget.contains("child")) {
            //TODO: Check if child supports its own sizing, if so simply pass the size to it (for example image, avoid wrapping it in another container)

            LayoutElementBuilders.Box.Builder builder = new LayoutElementBuilders.Box.Builder()
                    .setWidth(widget.contains("width") ? dp(widget.getFloat("width")) : expand())
                    .setHeight(widget.contains("height") ? dp(widget.getFloat("height")) : expand())
                    .addContent(translator.translate(context,widget.getNestedOrThrow("child"), deviceParameters))
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
