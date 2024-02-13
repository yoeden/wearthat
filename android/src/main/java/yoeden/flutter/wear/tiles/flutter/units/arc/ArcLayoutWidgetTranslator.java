package yoeden.flutter.wear.tiles.flutter.units.arc;

import static androidx.wear.tiles.ColorBuilders.argb;
import static androidx.wear.tiles.DimensionBuilders.degrees;
import static androidx.wear.tiles.DimensionBuilders.dp;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.DimensionBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.exceptions.UnsupportedTileException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;
import yoeden.flutter.wear.tiles.flutter.units.text.TextWidgetTranslator;

public class ArcLayoutWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__arc_layout";
    public final static String typeName = "ArcLayout";

    private final static String arcLineTypeId = "__arc_line";
    private final static String arcProgressLineTypeId = "__arc_progress_line";
    private final static String arcSpacerTypeId = "__arc_spacer";
    private final static String arcTextTypeId = "__arc_text";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
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
            final String type = child.getType();
            LayoutElementBuilders.ArcLayoutElement arcElement;

            if (type.equals(arcSpacerTypeId) || type.equals(arcTextTypeId) || type.equals(arcLineTypeId) || type.equals(arcProgressLineTypeId)) {
                internalTranslate(builder, child, deviceParameters);
            } else {
                arcElement = new LayoutElementBuilders.ArcAdapter.Builder()
                        .setContent(translator.translate(child, deviceParameters))
                        .build();
                builder.addContent(arcElement);
            }
        }

        return builder.build();

//        LayoutElementBuilders.ArcLine.Builder builder = new LayoutElementBuilders.ArcLine.Builder();
//        builder.setLength(degrees(90));
//        builder.setColor(argb(0xFF542E71));
//        builder.setThickness(dp(6));
//
//        return new LayoutElementBuilders.Arc.Builder()
//                .addContent(new LayoutElementBuilders.ArcLine.Builder()
//                        .setLength(degrees(130))
//                        .setColor(argb(0xAACFB7E1))
//                        .setThickness(dp(6))
//                        .build()
//                )
//                .addContent(new LayoutElementBuilders.ArcSpacer.Builder()
//                        .setLength(degrees(360 - 130))
//                        .build()
//                )
//                .addContent(builder.build())
//                .addContent(new LayoutElementBuilders.ArcSpacer.Builder()
//                        .setLength(degrees(180))
//                        .build()
//                )
//                .addContent(new LayoutElementBuilders.ArcText.Builder()
//                        .setText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
//                        .build()
//                )
//                .addContent(new LayoutElementBuilders.ArcAdapter.Builder().setContent(
//                        new AppIconWidgetTranslator().translate(translator, null, widget, deviceParameters)
//                ).build())
//                //Element starts at 12 o'clock or 0 degree position in the circle
//                .setAnchorAngle(degrees(0))
//                .setAnchorType(LayoutElementBuilders.ARC_ANCHOR_START)
//                .setVerticalAlign(VERTICAL_ALIGN_CENTER)
//                .build();
    }

    private void internalTranslate(
            LayoutElementBuilders.Arc.Builder builder,
            FlutterTileWidgetParcel child,
            DeviceParametersBuilders.DeviceParameters deviceParameters
    ) throws TileTranslationException {
        switch (child.getType()) {
            case arcLineTypeId:
                final LayoutElementBuilders.ArcLine.Builder lineBuilder = new LayoutElementBuilders.ArcLine.Builder();
                final float lineLength = child.getFloatOrThrow("length");

                lineBuilder.setLength(asDegrees(lineLength));

                if (child.contains("style")) {
                    final FlutterTileWidgetParcel style = child.getNested("style");
                    if (style.contains("color")) lineBuilder.setColor(argb(style.getIntOrThrow("color")));
                    if (style.contains("thickness")) lineBuilder.setThickness(dp(style.getFloatOrThrow("thickness")));
                }

                builder.addContent(lineBuilder.build());
                break;
            case arcProgressLineTypeId:
                final LayoutElementBuilders.ArcLine.Builder baseLineBuilder = new LayoutElementBuilders.ArcLine.Builder();
                final LayoutElementBuilders.ArcLine.Builder valueLineBuilder = new LayoutElementBuilders.ArcLine.Builder();

                final float baseLineLength = child.getFloatOrThrow("length");
                final float valueLineProgress = child.getFloatOrThrow("progress");
                //final float valueLineLength = (baseLineLength * valueLineProgress);
                final float progressDirection = child.getIntOrThrow("direction");
                final FlutterTileWidgetParcel style = child.getNested("style");

                baseLineBuilder.setLength(asDegrees(baseLineLength));
                valueLineBuilder.setLength(asDegrees(valueLineProgress));

                baseLineBuilder.setColor(argb(style.getIntOrThrow("backgroundColor")));
                valueLineBuilder.setColor(argb(style.getIntOrThrow("color")));

                baseLineBuilder.setThickness(dp(style.getFloatOrThrow("thickness")));
                valueLineBuilder.setThickness(dp(style.getFloatOrThrow("thickness")));

                if (progressDirection == 0) {
                    //Clockwise, progress going right to the circle
                    builder.addContent(baseLineBuilder.build())
                            .addContent(new LayoutElementBuilders.ArcSpacer.Builder().setLength(asDegrees(360 - baseLineLength)).build())
                            .addContent(valueLineBuilder.build());
                } else {
                    //Clockwise, progress going left to the circle
                    builder.addContent(baseLineBuilder.build())
                            .addContent(new LayoutElementBuilders.ArcSpacer.Builder().setLength(asDegrees(360 - baseLineLength + baseLineLength - valueLineProgress)).build())
                            .addContent(valueLineBuilder.build());
                }


                break;
            case arcSpacerTypeId:
                float spacerLength = child.getFloatOrThrow("length");

                builder.addContent(new LayoutElementBuilders.ArcSpacer.Builder()
                        .setLength(asDegrees(spacerLength))
                        .build());
                break;
            case arcTextTypeId:
                final LayoutElementBuilders.ArcText.Builder textBuilder = new LayoutElementBuilders.ArcText.Builder();
                final String text = child.getStringOrThrow("text");

                textBuilder.setText(text);
                if (child.contains("style")) {
                    final LayoutElementBuilders.FontStyle fontStyle = TextWidgetTranslator.tryStylingFont(child.getNestedOrThrow("style"), deviceParameters);
                    textBuilder.setFontStyle(fontStyle);
                }

                builder.addContent(textBuilder.build());
                break;
            default:
                throw new UnsupportedTileException("Unsupported tile: " + child.getType());
        }
    }

    private static DimensionBuilders.DegreesProp asDegrees(float degrees){
        return degrees(degrees);
    }
}
