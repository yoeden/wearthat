package yoeden.flutter.wear.exceptions;

public class UnsupportedValueException extends TileTranslationException{
    public UnsupportedValueException(String property,Object value)
    {
        super(String.format("Unsupported value '%s' given for property '%s'",value,property));
    }
}
