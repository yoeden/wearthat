package yoeden.flutter.wear.tiles.flutter;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import java.util.HashMap;
import java.util.Map;

import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.exceptions.UnknownTileWidget;
import yoeden.flutter.wear.tiles.flutter.units.AppIconWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.ButtonWidget;
import yoeden.flutter.wear.tiles.flutter.units.ChipWidget;
import yoeden.flutter.wear.tiles.flutter.units.CircularProgressIndicatorWidget;
import yoeden.flutter.wear.tiles.flutter.units.TitleChipWidget;
import yoeden.flutter.wear.tiles.flutter.units.layouts.MultiButtonLayoutWidget;
import yoeden.flutter.wear.tiles.flutter.units.layouts.MultiSlotLayoutWidget;
import yoeden.flutter.wear.tiles.flutter.units.layouts.PrimaryLayoutWidget;
import yoeden.flutter.wear.tiles.flutter.units.arc.ArcLayoutWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.ArcWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.ClickableWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.layouts.ColumnWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.DecoratedBoxWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.FractionallySizedBox;
import yoeden.flutter.wear.tiles.flutter.units.basic.ImageWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.basic.PaddingWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.layouts.RowWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.basic.SizedBoxWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.layouts.StackWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterTileWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.text.TextWidgetTranslator;


public class FlutterTileWidgetsTranslator {
    public static FlutterTileWidgetsTranslator getInstance() {
        return _instance;
    }

    private static final FlutterTileWidgetsTranslator _instance = new FlutterTileWidgetsTranslator();

    private final Map<String, FlutterTileWidgetTranslator> _translators = new HashMap<>();

    private FlutterTileWidgetsTranslator() {
        //Material Layouts
        _translators.put(PrimaryLayoutWidget.TypeId, new PrimaryLayoutWidget());
        _translators.put(MultiButtonLayoutWidget.TypeId, new MultiButtonLayoutWidget());
        _translators.put(MultiSlotLayoutWidget.TypeId, new MultiSlotLayoutWidget());

        //Basic Layouts
        _translators.put(ColumnWidgetTranslator.TypeId, new ColumnWidgetTranslator());
        _translators.put(StackWidgetTranslator.TypeId, new StackWidgetTranslator());
        _translators.put(ArcLayoutWidgetTranslator.TypeId, new ArcLayoutWidgetTranslator());
        _translators.put(RowWidgetTranslator.TypeId, new RowWidgetTranslator());

        //Arc
        _translators.put(ArcWidgetTranslator.TypeId, new ArcWidgetTranslator());

        //Chips
        _translators.put(ChipWidget.TypeId, new ChipWidget());
        _translators.put(TitleChipWidget.TypeId, new TitleChipWidget());

        //Basic
        _translators.put(ButtonWidget.TypeId, new ButtonWidget());
        _translators.put(SizedBoxWidgetTranslator.TypeId, new SizedBoxWidgetTranslator());
        _translators.put(ClickableWidgetTranslator.TypeId, new ClickableWidgetTranslator());
        _translators.put(TextWidgetTranslator.TypeId, new TextWidgetTranslator());
        _translators.put(PaddingWidgetTranslator.TypeId, new PaddingWidgetTranslator());
        _translators.put(DecoratedBoxWidgetTranslator.TypeId, new DecoratedBoxWidgetTranslator());
        _translators.put(ImageWidgetTranslator.TypeId, new ImageWidgetTranslator());
        _translators.put(FractionallySizedBox.TypeId, new FractionallySizedBox());
        _translators.put(AppIconWidgetTranslator.TypeId, new AppIconWidgetTranslator());
        _translators.put(CircularProgressIndicatorWidget.TypeId,new CircularProgressIndicatorWidget());
    }

    public LayoutElementBuilders.LayoutElement translate(
            Context context,
            FlutterTileWidgetParcel widget,
            ModifiersBuilders.Modifiers modifiers,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final String type = widget.getType();

        if (!_translators.containsKey(type)) {
            throw new UnknownTileWidget(type);
        }

        final FlutterTileWidgetTranslator translator = _translators.get(type);
        return translator.translate(this, context, modifiers, widget, deviceParameters);
    }

    public LayoutElementBuilders.LayoutElement translate(
            Context context,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {

        //TODO: Constant Modifiers
        return translate(context, widget, new ModifiersBuilders.Modifiers.Builder().build(), deviceParameters);
    }
}


