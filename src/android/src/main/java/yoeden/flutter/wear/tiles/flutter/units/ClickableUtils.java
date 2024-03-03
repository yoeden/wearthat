package yoeden.flutter.wear.tiles.flutter.units;

import androidx.wear.protolayout.ActionBuilders;
import androidx.wear.protolayout.ModifiersBuilders;
import androidx.wear.protolayout.StateBuilders;
import androidx.wear.protolayout.expression.AppDataKey;
import androidx.wear.protolayout.expression.DynamicBuilders;
import androidx.wear.protolayout.expression.DynamicDataBuilders;

import com.google.gson.Gson;

import yoeden.flutter.wear.TestService;
import yoeden.flutter.wear.tiles.flutter.FlutterTileWidgetParcel;
import yoeden.flutter.wear.tiles.flutter.exceptions.MissingPropertyException;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;

public class ClickableUtils {
    private static final String Action_LoadTile = "__loadtile";
    private static final String Action_PushNamed = "__pushnamed";
    private static final String Action_SetState = "__setstate";
    private static final String Action_Nothing = "__nothing";

    public static final AppDataKey<DynamicBuilders.DynamicString> KEY_WATER_INTAKE =
            new AppDataKey<>("water_intake");

    static ModifiersBuilders.Clickable buildClickable(FlutterTileWidgetParcel widget) throws MissingPropertyException {
        final FlutterTileWidgetParcel action = widget.getNestedOrThrow(CommonTileProperties.Action);

        final String route = action.getStringOrThrow("route");
        final Object args = action.get("args");

        if (action.isValue(CommonTileProperties.Action, Action_LoadTile)) {
            return new ModifiersBuilders.Clickable.Builder()
                    .setId(route)
                    .setOnClick(new ActionBuilders.LoadAction.Builder().build())
                    .build();
        } else if (action.isValue(CommonTileProperties.Action, Action_PushNamed)) {
            //TODO:
//            return new ModifiersBuilders.Clickable.Builder()
//                    .setId(route)
//                    .setOnClick(new ActionBuilders.LaunchAction.Builder()
//                            .setAndroidActivity(new ActionBuilders.AndroidActivity.Builder()
//                                    .setClassName(Kaka.Activity.getName())
//                                    .setPackageName(Kaka.Activity.getPackage().getName())
//                                    .build())
//                            .build())
//                    .build();
            return new ModifiersBuilders.Clickable.Builder()
                    .setId(route)
                    .setOnClick(new ActionBuilders.LaunchAction.Builder()
                            .setAndroidActivity(new ActionBuilders.AndroidActivity.Builder()
                                    .setClassName(TestService.class.getName())
                                    .setPackageName(TestService.class.getPackage().getName())
                                    .build())
                            .build())
                    .build();

        } else if (action.isValue(CommonTileProperties.Action, Action_Nothing)) {
            return new ModifiersBuilders.Clickable.Builder()
                    .setOnClick(new ActionBuilders.LoadAction.Builder().build())
                    .build();
        } else if (action.isValue(CommonTileProperties.Action, Action_SetState)) {
            final String j = new Gson().toJson(action.get("state"));
            return new ModifiersBuilders.Clickable.Builder()
                    .setOnClick(new ActionBuilders.LoadAction.Builder()
                            .setRequestState(new StateBuilders.State.Builder()
                                    .addKeyToValueMapping(
                                            KEY_WATER_INTAKE,
                                            DynamicDataBuilders.DynamicDataValue.fromString(j)
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build();
        } else {
            throw new MissingPropertyException("Clickable-Action", action.getString(CommonTileProperties.Action));
        }
    }
}
