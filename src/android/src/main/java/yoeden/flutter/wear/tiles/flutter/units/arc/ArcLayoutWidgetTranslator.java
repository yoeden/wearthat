package yoeden.flutter.wear.tiles.flutter.units.arc;

import static androidx.wear.protolayout.ColorBuilders.argb;
import static androidx.wear.protolayout.DimensionBuilders.degrees;
import static androidx.wear.protolayout.DimensionBuilders.dp;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.DimensionBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.material.CircularProgressIndicator;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.exceptions.UnsupportedTileException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.units.text.TextWidgetTranslator;

public class ArcLayoutWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__arc_layout";
    public final static String typeName = "ArcLayout";

    private final static String arcLineTypeId = "__arc_line";
    private final static String arcProgressLineTypeId = "__arc_progress_line";
    private final static String arcSpacerTypeId = "__arc_spacer";
    private final static String arcTextTypeId = "__arc_text";
    private final static String arcAdapterTypeId = "__arc_adapter";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        LayoutElementBuilders.Arc.Builder builder = new LayoutElementBuilders.Arc.Builder()
                .setAnchorType(LayoutElementBuilders.ARC_ANCHOR_START);

        if (modifiers != null) {
            builder.setModifiers(modifiers);
        }

        if (widget.contains("alignment")) builder.setVerticalAlign(widget.getIntOrThrow("alignment"));
        if (widget.contains("anchor")) builder.setAnchorAngle(asDegrees(widget.getFloatOrThrow("anchor")));

        final List<FlutterTileWidgetParcel> children = widget.getChildren();
        for (FlutterTileWidgetParcel child : children) {
            internalTranslate(translator, context, modifiers, builder, child, deviceParameters);
        }

        return builder.build();
    }

    private void internalTranslate(
            FlutterTileWidgetsTranslator translator,
            Context context,
            ModifiersBuilders.Modifiers modifiers,
            LayoutElementBuilders.Arc.Builder builder,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters
    ) throws TileTranslationException {
        switch (widget.getType()) {
            case arcLineTypeId:
                final LayoutElementBuilders.ArcLine.Builder lineBuilder = new LayoutElementBuilders.ArcLine.Builder();
                final float lineLength = widget.getFloatOrThrow("length");

                lineBuilder.setLength(asDegrees(lineLength));

                if (widget.contains("style")) {
                    final FlutterTileWidgetParcel style = widget.getNested("style");
                    if (style.contains("color")) lineBuilder.setColor(argb(style.getIntOrThrow("color")));
                    if (style.contains("thickness")) lineBuilder.setThickness(dp(style.getFloatOrThrow("thickness")));
                }

                builder.addContent(lineBuilder.build());
                break;
            case arcProgressLineTypeId:
                final LayoutElementBuilders.ArcLine.Builder baseLineBuilder = new LayoutElementBuilders.ArcLine.Builder();
                final LayoutElementBuilders.ArcLine.Builder valueLineBuilder = new LayoutElementBuilders.ArcLine.Builder();

                final float baseLineLength = widget.getFloatOrThrow("length");
                final float valueLineLine = widget.getFloatOrThrow("progress");
                final float progressDirection = widget.getIntOrThrow("direction");
                final FlutterTileWidgetParcel style = widget.getNested("style");

                baseLineBuilder.setLength(asDegrees(baseLineLength));
                valueLineBuilder.setLength(asDegrees(valueLineLine));

                baseLineBuilder.setColor(argb(style.getIntOrThrow("backgroundColor")));
                valueLineBuilder.setColor(argb(style.getIntOrThrow("color")));

                baseLineBuilder.setThickness(dp(style.getFloatOrThrow("thickness")));
                valueLineBuilder.setThickness(dp(style.getFloatOrThrow("thickness")));

                if (progressDirection == 0) {
                    //Clockwise, progress going right to the circle
                    builder.addContent(baseLineBuilder.build())
                            .addContent(new LayoutElementBuilders.ArcSpacer.Builder().setLength(asDegrees(360 - baseLineLength)).build())
                            .addContent(valueLineBuilder.build())
                            .addContent(new LayoutElementBuilders.ArcSpacer.Builder().setLength(asDegrees(baseLineLength - valueLineLine)).build());
                } else {
                    //Counter Clockwise, progress going left to the circle
                    builder.addContent(baseLineBuilder.build())
                            .addContent(new LayoutElementBuilders.ArcSpacer.Builder().setLength(asDegrees(360 - baseLineLength + baseLineLength - valueLineLine)).build())
                            .addContent(valueLineBuilder.build());
                }

                break;
            case arcSpacerTypeId:
                float spacerLength = widget.getFloatOrThrow("length");

                builder.addContent(new LayoutElementBuilders.ArcSpacer.Builder()
                        .setLength(asDegrees(spacerLength))
                        .build());
                break;
            case arcTextTypeId:
                final LayoutElementBuilders.ArcText.Builder textBuilder = new LayoutElementBuilders.ArcText.Builder();
                final String text = widget.getStringOrThrow("text");

                textBuilder.setText(text);
                if (widget.contains("style")) {
                    final LayoutElementBuilders.FontStyle fontStyle = TextWidgetTranslator.tryStylingFont(widget.getNestedOrThrow("style"), deviceParameters);
                    textBuilder.setFontStyle(fontStyle);
                }

                builder.addContent(textBuilder.build());
                break;
            case arcAdapterTypeId:
                final LayoutElementBuilders.ArcAdapter.Builder adapterBuilder = new LayoutElementBuilders.ArcAdapter.Builder();
                adapterBuilder.setContent(translator.translate(context, widget.getChild(), modifiers, deviceParameters));

                builder.addContent(adapterBuilder.build());
                break;
            default:
                throw new UnsupportedTileException("Unsupported tile: " + widget.getType());
        }
    }

    private static DimensionBuilders.DegreesProp asDegrees(float degrees) {
        return degrees(degrees);
    }
}
