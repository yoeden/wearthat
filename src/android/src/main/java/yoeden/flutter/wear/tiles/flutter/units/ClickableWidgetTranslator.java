package yoeden.flutter.wear.tiles.flutter.units;

import androidx.annotation.NonNull;

import androidx.wear.protolayout.ActionBuilders;
import androidx.wear.protolayout.DeviceParametersBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.StateBuilders;
import androidx.wear.protolayout.expression.AppDataKey;
import androidx.wear.protolayout.expression.DynamicBuilders;
import androidx.wear.protolayout.expression.DynamicDataBuilders;

import com.google.gson.Gson;

import yoeden.flutter.wear.tiles.flutter.exceptions.MissingPropertyException;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterModifierTileWidgetTranslator;

public class ClickableWidgetTranslator extends FlutterModifierTileWidgetTranslator {
    public final static String TypeId = "__clickable";
    public final static String typeName = "Clickable";

    @Override
    protected void translate(
            ModifiersBuilders.Modifiers.Builder builder,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        builder.setClickable(ClickableUtils.buildClickable(widget));
    }

    @NonNull
    private ModifiersBuilders.Modifiers.Builder NavigateRoute(ModifiersBuilders.Modifiers.Builder builder, String route, Object args) {
        return builder.setClickable(
                new ModifiersBuilders.Clickable.Builder()
                        .setId(route + "*")
                        .setOnClick(new ActionBuilders.LoadAction.Builder().build())
                        .build()
        );

//        return builder.setClickable(
//                new ModifiersBuilders.Clickable.Builder()
//                        .setId(route)
//                        .setOnClick(
//                                new ActionBuilders.LaunchAction.Builder()
//                                        .setAndroidActivity(
//                                                new ActionBuilders.AndroidActivity.Builder()
//                                                        //TODO: Check how to make this dynamic
//                                                        .setClassName(TestActivity.class.getName())
//                                                        .setPackageName(TestActivity.class.getPackage().getName())
//                                                        .addKeyToExtraMapping("route", ActionBuilders.stringExtra(route))
//                                                        .build()
//                                        )
//                                        .build()
//                        )
//                        .build()
//        );
    }

    @NonNull
    private ModifiersBuilders.Modifiers.Builder LoadTile(ModifiersBuilders.Modifiers.Builder builder, String route) {
        return builder.setClickable(
                new ModifiersBuilders.Clickable.Builder()
                        .setId(route)
                        .setOnClick(
                                new ActionBuilders.LoadAction.Builder()
                                        .build()
                        )
                        .build()
        );
    }

    @NonNull
    private ModifiersBuilders.Modifiers.Builder Nothing(ModifiersBuilders.Modifiers.Builder builder) {
        return builder.setClickable(
                new ModifiersBuilders.Clickable.Builder()
                        .build()
        );
    }
}
