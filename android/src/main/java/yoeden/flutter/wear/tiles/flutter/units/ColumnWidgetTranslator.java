package yoeden.flutter.wear.tiles.flutter.units;

import static androidx.wear.tiles.DimensionBuilders.expand;
import static androidx.wear.tiles.DimensionBuilders.wrap;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import java.util.List;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.exceptions.UnsupportedValueException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetsTranslator;

public class ColumnWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__column";
    public final static String typeName = "Column";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {


        LayoutElementBuilders.Column.Builder builder = new LayoutElementBuilders.Column.Builder();

        final int alignment = widget.getIntOrThrow("alignment");
        switch (alignment) {
            case 0:
                builder.setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_START);
                break;
            case 1:
                builder.setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_END);
                break;
            case 2:
                builder.setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER);
                break;
            default:
                throw new UnsupportedValueException("alignment", alignment);
        }
        final int size = widget.getIntOrThrow("size");
        switch (size) {
            case 0:
                builder.setHeight(wrap())
                        .setWidth(wrap());
                break;
            case 1:
                builder.setHeight(expand())
                        .setWidth(expand());
                break;
        }

        //TODO: base class for all
        if (modifiers != null) {
            builder.setModifiers(modifiers);
        }

        final List<FlutterTileWidgetParcel> children = widget.getChildren();
        for (FlutterTileWidgetParcel child : children) {
            builder.addContent(translator.translate(child, deviceParameters));
        }

        return builder.build();
    }
}


