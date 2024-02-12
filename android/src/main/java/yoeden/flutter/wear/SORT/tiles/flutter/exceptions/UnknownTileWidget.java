package yoeden.flutter.wear.SORT.tiles.flutter.exceptions;

public final class UnknownTileWidget extends TileTranslationException {
    public UnknownTileWidget(String type) {
        super("Unknown tile widget received: " + type);
    }
}
