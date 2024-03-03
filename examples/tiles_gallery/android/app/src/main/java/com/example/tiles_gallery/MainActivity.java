package com.example.tiles_gallery;

import android.os.Bundle;

import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;

import yoeden.flutter.wear.tiles.flutter.units.ClickableWidgetTranslator;
import yoeden.flutter.wear.tiles.flutter.units.Kaka;
import yoeden.flutter.wear.tiles.services.KnownTiles;

public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Kaka.Activity = TestActivity.class;
    }
}
