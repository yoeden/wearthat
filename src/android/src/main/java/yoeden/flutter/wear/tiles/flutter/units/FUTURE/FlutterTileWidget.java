package yoeden.flutter.wear.tiles.flutter.units.FUTURE;

import android.content.Context;

import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.LayoutElementBuilders;
import androidx.wear.protolayout.ModifiersBuilders;

import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;

public class FlutterTileWidget {

    private final int flags;

    public FlutterTileWidget(int flags)
    {
        this.flags = flags;
    }

    public LayoutElementBuilders.LayoutElement fromParcel(Context context,
                                                          FlutterTileWidgetParcel widget,
                                                          ModifiersBuilders.Modifiers modifiers,
                                                          DeviceParametersBuilders.DeviceParameters deviceParameters)
    {
        throw new RuntimeException("Not implemented");
    }
}

