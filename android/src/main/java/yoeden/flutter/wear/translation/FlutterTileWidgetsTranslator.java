package yoeden.flutter.wear.translation;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import java.util.HashMap;
import java.util.Map;

import yoeden.flutter.wear.exceptions.TileTranslationException;
import yoeden.flutter.wear.exceptions.UnknownTileWidget;
import yoeden.flutter.wear.translation.units.AppIconWidgetTranslator;
import yoeden.flutter.wear.translation.units.arc.ArcLayoutWidgetTranslator;
import yoeden.flutter.wear.translation.units.ArcWidgetTranslator;
import yoeden.flutter.wear.translation.units.ClickableWidgetTranslator;
import yoeden.flutter.wear.translation.units.ColumnWidgetTranslator;
import yoeden.flutter.wear.translation.units.DecoratedBoxWidgetTranslator;
import yoeden.flutter.wear.translation.units.FractionallySizedBox;
import yoeden.flutter.wear.translation.units.ImageWidgetTranslator;
import yoeden.flutter.wear.translation.units.PaddingWidgetTranslator;
import yoeden.flutter.wear.translation.units.RowWidgetTranslator;
import yoeden.flutter.wear.translation.units.SizedBoxWidgetTranslator;
import yoeden.flutter.wear.translation.units.StackWidgetTranslator;
import yoeden.flutter.wear.translation.units.text.TextWidgetTranslator;


public class FlutterTileWidgetsTranslator {
    public static FlutterTileWidgetsTranslator getInstance() {
        return _instance;
    }

    private static final FlutterTileWidgetsTranslator _instance = new FlutterTileWidgetsTranslator();

    private final Map<String, FlutterTileWidgetTranslator> _translators = new HashMap<>();

    private FlutterTileWidgetsTranslator() {
        _translators.put(SizedBoxWidgetTranslator.typeId, new SizedBoxWidgetTranslator());
        _translators.put(ClickableWidgetTranslator.typeId, new ClickableWidgetTranslator());
        _translators.put(TextWidgetTranslator.typeId, new TextWidgetTranslator());
        _translators.put(ColumnWidgetTranslator.typeId, new ColumnWidgetTranslator());
        _translators.put(RowWidgetTranslator.typeId, new RowWidgetTranslator());
        _translators.put(StackWidgetTranslator.typeId, new StackWidgetTranslator());
        _translators.put(PaddingWidgetTranslator.typeId, new PaddingWidgetTranslator());
        _translators.put(DecoratedBoxWidgetTranslator.typeId, new DecoratedBoxWidgetTranslator());
        _translators.put(ImageWidgetTranslator.typeId, new ImageWidgetTranslator());
        _translators.put(ArcWidgetTranslator.typeId, new ArcWidgetTranslator());
        _translators.put(FractionallySizedBox.typeId, new FractionallySizedBox());
        _translators.put(AppIconWidgetTranslator.typeId, new AppIconWidgetTranslator());
        _translators.put(ArcLayoutWidgetTranslator.typeId, new ArcLayoutWidgetTranslator());
    }

    public LayoutElementBuilders.LayoutElement translate(FlutterTileWidgetParcel widget, ModifiersBuilders.Modifiers modifiers,
                                                         DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final String type = widget.getType();

        if(!_translators.containsKey(type)){
            throw new UnknownTileWidget(type);
        }

        final FlutterTileWidgetTranslator translator = _translators.get(type);
        return translator.translate(this, modifiers, widget,deviceParameters);
    }

    public LayoutElementBuilders.LayoutElement translate(FlutterTileWidgetParcel widget,
                                                         DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        return translate(widget,null, deviceParameters);
    }
}


