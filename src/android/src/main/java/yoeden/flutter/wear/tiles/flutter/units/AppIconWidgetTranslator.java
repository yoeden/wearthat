package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.dp;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class AppIconWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__appicon";
    public final static String typeName = "AppIcon";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context, ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final LayoutElementBuilders.Image.Builder builder = new LayoutElementBuilders.Image.Builder()
                .setWidth(dp(20))
                .setHeight(dp(20));

        builder.setResourceId("__app_icon");

        if (widget.contains("background")) {
            final int color = widget.getInt("background");
            final ModifiersBuilders.Background.Builder backgroundBuilder = new ModifiersBuilders.Background.Builder();
            backgroundBuilder.setColor(argb(color));
            backgroundBuilder.setCorner(new ModifiersBuilders.Corner.Builder().setRadius(dp(200)).build());

            if (modifiers != null) {
                modifiers = new ModifiersBuilders.Modifiers.Builder()
                        .setBackground(backgroundBuilder.build())
                        .setBorder(modifiers.getBorder())
                        .setClickable(modifiers.getClickable())
                        .setSemantics(modifiers.getSemantics())
                        .setPadding(new ModifiersBuilders.Padding.Builder().setAll(dp(4)).build())
                        .build()
                ;
            } else {
                modifiers = new ModifiersBuilders.Modifiers.Builder()
                        .setBackground(backgroundBuilder.build())
                        .setPadding(new ModifiersBuilders.Padding.Builder().setAll(dp(4)).build())
                        .build();
            }


        }

        if(modifiers != null) builder.setModifiers(modifiers);

        return builder.build();
    }
}
