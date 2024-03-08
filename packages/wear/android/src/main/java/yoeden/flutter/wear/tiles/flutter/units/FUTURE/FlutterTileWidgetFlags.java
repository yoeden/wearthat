package yoeden.flutter.wear.tiles.flutter.units.FUTURE;

public abstract class FlutterTileWidgetFlags {
    public static final int HAS_SIZE = 1 << 0;
    public static final int TEST = 1 << 1;

    public static boolean has(int flags, int flag)
    {
        return (flags & flag) != 0;
    }
}
