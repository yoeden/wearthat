package yoeden.flutter.wear.tiles.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class KnownTiles {

    private static final HashMap<String, Class<?>> tiles = new HashMap<>();

    private KnownTiles() {

    }

    public static void addTile(String name, Class<?> tileClass) {
        tiles.putIfAbsent(name, tileClass);
    }

    public static Class<?> getTile(String name)
    {
        return tiles.getOrDefault(name,null);
    }
 }
