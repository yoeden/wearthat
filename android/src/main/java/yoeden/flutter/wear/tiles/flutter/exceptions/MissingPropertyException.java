package yoeden.flutter.wear.tiles.flutter.exceptions;

public final class MissingPropertyException extends TileTranslationException {
    public MissingPropertyException(String type,String name) {
        super("Property " + name + " was not supplied for type " + type);
    }
}
