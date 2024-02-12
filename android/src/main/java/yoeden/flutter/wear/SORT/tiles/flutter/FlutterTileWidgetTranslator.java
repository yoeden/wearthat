package yoeden.flutter.wear.SORT.tiles.flutter;

import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.ModifiersBuilders;

import yoeden.flutter.wear.SORT.tiles.flutter.exceptions.TileTranslationException;

public interface FlutterTileWidgetTranslator {

    LayoutElementBuilders.LayoutElement translate(
            FlutterTileWidgetsTranslator translator,
            ModifiersBuilders.Modifiers modifiers,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters)
            throws TileTranslationException;
}
