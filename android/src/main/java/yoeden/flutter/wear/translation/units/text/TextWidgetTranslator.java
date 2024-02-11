package yoeden.flutter.wear.translation.units.text;

import static androidx.wear.tiles.ColorBuilders.argb;
import static androidx.wear.tiles.DimensionBuilders.em;
import static androidx.wear.tiles.DimensionBuilders.sp;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.exceptions.MissingPropertyException;
import yoeden.flutter.wear.exceptions.TileTranslationException;
import yoeden.flutter.wear.exceptions.UnsupportedValueException;
import yoeden.flutter.wear.translation.FlutterTileWidgetParcel;
import yoeden.flutter.wear.translation.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.translation.FlutterTileWidgetsTranslator;

public class TextWidgetTranslator implements FlutterTileWidgetTranslator {
    public final static String typeId = "__text";
    public final static String typeName = "Text";

    @Override
    public LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        final String text = widget.getStringOrThrow("text");

        LayoutElementBuilders.Text.Builder builder = new LayoutElementBuilders.Text.Builder()
                .setText(text);

        if (modifiers != null) builder.setModifiers(modifiers);

        final LayoutElementBuilders.FontStyle style = tryStylingFont(widget, deviceParameters);
        if(style != null) builder.setFontStyle(style);

        setTextAlignment(widget, builder);
        setTextOverflow(widget, builder);
        setMaxLines(widget, builder);

        return builder.build();
    }

    private void setMaxLines(FlutterTileWidgetParcel widget, LayoutElementBuilders.Text.Builder builder) throws MissingPropertyException {
        final int maxLines = widget.getIntOrThrow("maxLines");
        builder.setMaxLines(maxLines);
    }

    private void setTextAlignment(FlutterTileWidgetParcel widget, LayoutElementBuilders.Text.Builder builder) throws MissingPropertyException {
        final int align = widget.getIntOrThrow("align");
        int textAlign = LayoutElementBuilders.TEXT_ALIGN_UNDEFINED;
        if (align == 0) textAlign = LayoutElementBuilders.TEXT_ALIGN_CENTER;
        if (align == 1) textAlign = LayoutElementBuilders.TEXT_ALIGN_START;
        if (align == 2) textAlign = LayoutElementBuilders.TEXT_ALIGN_END;
        builder.setMultilineAlignment(textAlign);
    }

    private void setTextOverflow(FlutterTileWidgetParcel widget, LayoutElementBuilders.Text.Builder builder) throws MissingPropertyException {
        final int overflow = widget.getIntOrThrow("overflow");
        int textOverflow = LayoutElementBuilders.TEXT_OVERFLOW_UNDEFINED;
        if (overflow == 0) textOverflow = LayoutElementBuilders.TEXT_OVERFLOW_ELLIPSIZE_END;
        if (overflow == 1) textOverflow = LayoutElementBuilders.TEXT_OVERFLOW_TRUNCATE;
        builder.setOverflow(textOverflow);
    }

    private static final int CUSTOM_STYLE = 0;

    public static LayoutElementBuilders.FontStyle tryStylingFont(FlutterTileWidgetParcel widget, DeviceParametersBuilders.DeviceParameters deviceParameters) throws UnsupportedValueException {
        LayoutElementBuilders.FontStyle.Builder fontStyleBuilder = new LayoutElementBuilders.FontStyle.Builder();

        if (widget.contains("style")) {
            final FlutterTileWidgetParcel style = widget.getNested("style");
            final int type = style.getInt("type");


            if (type != CUSTOM_STYLE) {
                switch (type) {
                    case 1:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.display1(deviceParameters);
                        break;
                    case 2:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.display2(deviceParameters);
                        break;
                    case 3:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.display3(deviceParameters);
                        break;
                    case 4:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.title1(deviceParameters);
                        break;
                    case 5:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.title2(deviceParameters);
                        break;
                    case 6:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.title3(deviceParameters);
                        break;
                    case 7:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.body1(deviceParameters);
                        break;
                    case 8:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.body2(deviceParameters);
                        break;
                    case 9:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.button(deviceParameters);
                        break;
                    case 10:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.caption1(deviceParameters);
                        break;
                    case 11:
                        fontStyleBuilder = LayoutElementBuilders.FontStyles.caption2(deviceParameters);
                        break;
                }

            }
            //fontStyleBuilder = new LayoutElementBuilders.FontStyle.Builder();
            if (style.contains("size")) fontStyleBuilder.setSize(sp(style.getFloat("size")));
            if (style.contains("color")) fontStyleBuilder.setColor(argb(style.getInt("color")));
            if (style.contains("italic")) fontStyleBuilder.setItalic(style.getBool("italic"));
            if (style.contains("weight")) {
                final int weight = style.getInt("weight");
                if(weight != 400 && weight != 500 && weight != 700) throw new UnsupportedValueException("weight",weight);
                fontStyleBuilder.setWeight(weight);
            }
            if (style.contains("letterSpacing")) fontStyleBuilder.setLetterSpacing(em((style.getFloat("letterSpacing"))));

            return fontStyleBuilder.build();
            //builder.setFontStyle(fontStyleBuilder.build());
        }

        return null;
    }
}
