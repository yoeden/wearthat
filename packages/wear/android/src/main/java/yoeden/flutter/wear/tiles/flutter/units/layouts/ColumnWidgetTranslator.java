package yoeden.flutter.wear.tiles.flutter.units.layouts;

import static androidx.wear.protolayout.DimensionBuilders.expand;
import static androidx.wear.protolayout.DimensionBuilders.wrap;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.exceptions.MissingPropertyException;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.exceptions.UnsupportedValueException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class ColumnWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String TypeId = "__column";
    public final static String typeName = "Column";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            Context context, ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        LayoutElementBuilders.Column.Builder columnBuilder = new LayoutElementBuilders.Column.Builder();
        LayoutElementBuilders.Box.Builder boxBuilder = new LayoutElementBuilders.Box.Builder();

        handleMainAxisAlignment(widget, boxBuilder);
        handleCrossAxisAlignment(widget, boxBuilder);
        handleSize(widget, columnBuilder, boxBuilder);

        //TODO: base class for all
        if (modifiers != null) columnBuilder.setModifiers(modifiers);

        final List<FlutterTileWidgetParcel> children = widget.getChildren();
        for (FlutterTileWidgetParcel child : children) {
            columnBuilder.addContent(translator.translate(context, child, deviceParameters));
        }

        boxBuilder.addContent(columnBuilder.build());
        return boxBuilder.build();
    }

    private static void handleSize(
            FlutterTileWidgetParcel widget,
            LayoutElementBuilders.Column.Builder rowBuilder,
            LayoutElementBuilders.Box.Builder boxBuilder) throws MissingPropertyException {
        final int size = widget.getIntOrThrow(CommonTileProperties.MainAxisSize);

        rowBuilder.setHeight(wrap());
        rowBuilder.setWidth(wrap());

        switch (size) {
            case 0:
                boxBuilder
                        .setHeight(wrap())
                        .setWidth(wrap());
                break;
            case 1:
                boxBuilder
                        .setHeight(expand())
                        .setWidth(expand());
                break;
        }
    }

    private static void handleMainAxisAlignment(FlutterTileWidgetParcel widget, LayoutElementBuilders.Box.Builder boxBuilder) throws MissingPropertyException, UnsupportedValueException {
        final int alignment = widget.getIntOrThrow(CommonTileProperties.MainAxisAlignment);
        switch (alignment) {
            case 0:
                boxBuilder.setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_TOP);
                break;
            case 1:
                boxBuilder.setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_BOTTOM);
                break;
            case 2:
                boxBuilder.setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER);
                break;
            default:
                throw new UnsupportedValueException("alignment", alignment);
        }
    }

    private static void handleCrossAxisAlignment(FlutterTileWidgetParcel widget, LayoutElementBuilders.Box.Builder boxBuilder) throws MissingPropertyException, UnsupportedValueException {
        final int alignment = widget.getIntOrThrow(CommonTileProperties.CrossAxisAlignment);
        switch (alignment) {
            case 0:
                boxBuilder.setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_START);
                break;
            case 1:
                boxBuilder.setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_END);
                break;
            case 2:
                boxBuilder.setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER);
                break;
            default:
                throw new UnsupportedValueException("alignment", alignment);
        }
    }
}


