package yoeden.flutter.wear.exceptions;

public final class UnknownTileWidget extends TileTranslationException {
    public UnknownTileWidget(String type) {
        super("Unknown tile widget received: " + type);
    }
}
