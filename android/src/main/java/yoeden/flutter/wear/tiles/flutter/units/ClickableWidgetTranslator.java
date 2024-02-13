package yoeden.flutter.wear.tiles.flutter.units;

import androidx.annotation.NonNull;
import androidx.wear.tiles.ActionBuilders;
import androidx.wear.tiles.DeviceParametersBuilders;
import androidx.wear.tiles.ModifiersBuilders;


import yoeden.flutter.wear.tiles.flutter.exceptions.MissingPropertyException;
import yoeden.flutter.wear.tiles.flutter.exceptions.TileTranslationException;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.units.base.FlutterModifierTileWidgetTranslator;

public class ClickableWidgetTranslator extends FlutterModifierTileWidgetTranslator {
    public final static String typeId = "__clickable";
    public final static String typeName = "Clickable";
    public static Class<?> MainActivity;

    @Override
    protected void translate(
            ModifiersBuilders.Modifiers.Builder builder,
            FlutterTileWidgetParcel widget,
            DeviceParametersBuilders.DeviceParameters deviceParameters) throws TileTranslationException {
        final FlutterTileWidgetParcel action = widget.getNestedOrThrow("action");

        final String route = action.getStringOrThrow("route");
        final Object args = action.get("args");

        if (action.isValue("action", "__loadtile")) {
            LoadTile(builder, route)
                    .build();
        } else if (action.isValue("action", "__pushnamed")) {
            NavigateRoute(builder, route, args)
                    .build();
        } else if (action.isValue("action", "__nothing")) {
            Nothing(builder).build();
        } else {
            throw new MissingPropertyException(typeName, "action");
        }
    }

    @NonNull
    private ModifiersBuilders.Modifiers.Builder NavigateRoute(ModifiersBuilders.Modifiers.Builder builder, String route, Object args) {
        return builder.setClickable(
                new ModifiersBuilders.Clickable.Builder()
                        .setId(route)
                        .setOnClick(
                                new ActionBuilders.LaunchAction.Builder()
                                        .setAndroidActivity(
                                                new ActionBuilders.AndroidActivity.Builder()
                                                        //TODO: Check how to make this dynamic
                                                        .setClassName(MainActivity.getName())
                                                        .setPackageName(MainActivity.getPackage().getName())
                                                        .addKeyToExtraMapping("route", ActionBuilders.stringExtra(route ))
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
        );
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
