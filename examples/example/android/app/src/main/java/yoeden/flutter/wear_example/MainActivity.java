package yoeden.flutter.wear_example;

import android.os.Bundle;

import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;
import yoeden.flutter.wear.tiles.flutter.units.ClickableWidgetTranslator;
import yoeden.flutter.wear.tiles.services.KnownTiles;
import yoeden.flutter.wear_example.tiles.HelloTile;

public class MainActivity extends FlutterActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        KnownTiles.addTile("hellotile", HelloTile.class);

        super.onCreate(savedInstanceState);
    }
}
