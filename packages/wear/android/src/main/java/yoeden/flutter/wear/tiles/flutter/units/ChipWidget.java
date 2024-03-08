package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.protolayout.DimensionBuilders.wrap;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.Button;
import androidx.wear.protolayout.material.Chip;
import androidx.wear.protolayout.material.ChipColors;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;

public class ChipWidget implements FlutterTileWidgetTranslator {
    public static final String TypeId = "__chip";

    @Override
    public LayoutElementBuilders.LayoutElement translate(FlutterTileWidgetsTranslator translator, Context context, ModifiersBuilders.Modifiers modifiers, FlutterTileWidgetParcel widget, DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final Chip.Builder builder = new Chip.Builder(
                context,
                ClickableUtils.buildClickable(widget),
                deviceParameters
        );

//        builder.setWidth(wrap());
        //if (widget.contains("icon")) builder.setIconContent(widget.getString("icon"));
        //builder.setCustomContent(translator.translate(context, widget.getChild(), deviceParameters));
        builder.setPrimaryLabelContent("10");

        //TODO:
        //builder.setChipColors(new ChipColors());

        return builder.build();
    }
}
