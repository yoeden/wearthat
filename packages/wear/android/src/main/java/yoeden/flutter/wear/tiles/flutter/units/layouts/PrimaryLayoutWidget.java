package yoeden.flutter.wear.tiles.flutter.units.layouts;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.layouts.PrimaryLayout;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;

public class PrimaryLayoutWidget implements FlutterTileWidgetTranslator {
    public static final String TypeId = "__primary_layout";

    @Override
    public LayoutElementBuilders.LayoutElement translate(FlutterTileWidgetsTranslator translator, Context context, ModifiersBuilders.Modifiers modifiers, FlutterTileWidgetParcel widget, DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final PrimaryLayout.Builder builder = new PrimaryLayout.Builder(deviceParameters);

        if (widget.contains(CommonTileProperties.Body))
            builder.setContent(translator.translate(context, widget.getNested(CommonTileProperties.Body), deviceParameters));

        if (widget.contains(CommonTileProperties.Chip))
            builder.setPrimaryChipContent(translator.translate(context, widget.getNested(CommonTileProperties.Chip), deviceParameters));

        if (widget.contains(CommonTileProperties.Title))
            builder.setPrimaryLabelTextContent(translator.translate(context, widget.getNested(CommonTileProperties.Title), deviceParameters));

        if (widget.contains(CommonTileProperties.SubTitle))
            builder.setSecondaryLabelTextContent(translator.translate(context, widget.getNested(CommonTileProperties.SubTitle), deviceParameters));

        return builder.build();
    }
}
