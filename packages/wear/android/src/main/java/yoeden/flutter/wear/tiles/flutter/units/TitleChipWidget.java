package yoeden.flutter.wear.tiles.flutter.units;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.TitleChip;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;

public class TitleChipWidget implements FlutterTileWidgetTranslator {
    public static final String TypeId = "__titlechip";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters)
            throws TileTranslationException {

        final String title = widget.getStringOrThrow(CommonTileProperties.Title);

        final TitleChip.Builder builder = new TitleChip.Builder(
                context,
                title,
                ClickableUtils.buildClickable(widget),
                deviceParameters);

        if (widget.contains("icon")) builder.setIconContent(widget.getString("icon"));

        //TODO:
//        builder.setChipColors();
//        builder.setWidth();
//        builder.setHorizontalAlignment();

        return builder.build();
    }
}
