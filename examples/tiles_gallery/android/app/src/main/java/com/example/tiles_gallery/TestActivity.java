package com.example.tiles_gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import yoeden.flutter.wear.FlutterWearTiles;
import yoeden.flutter.wear.tiles.services.FlutterTileService;

public class TestActivity extends FlutterActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(FlutterWearTiles.Tag,"Fuck yea !!");
        navigateToFlutterRoute("/TEST","KAKA");
    }

    private void navigateToFlutterRoute(String routeName, String argument) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(Intent.ACTION_RUN);
        intent.putExtra("route", routeName);
        intent.putExtra("argument", argument);
        intent.putExtra("arguments", argument);
        intent.putExtra("args", argument);
        intent.putExtra("aaaaa", argument);
        startActivity(intent);
    }
}
