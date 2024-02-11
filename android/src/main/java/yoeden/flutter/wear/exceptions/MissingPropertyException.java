package yoeden.flutter.wear.exceptions;

public final class MissingPropertyException extends TileTranslationException {
    public MissingPropertyException(String type,String name) {
        super("Property " + name + " was not supplied for type " + type);
    }
}
