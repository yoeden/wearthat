package yoeden.flutter.wear.tiles.flutter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yoeden.flutter.wear.tiles.flutter.exceptions.MissingPropertyException;
import yoeden.flutter.wear.tiles.flutter.units.base.CommonTileProperties;

public class FlutterTileWidgetParcel {
    private final Map<String, Object> _map;

    public FlutterTileWidgetParcel(Map<String, Object> map) {
        _map = map;
    }

    public String getType() throws MissingPropertyException {
        if (!_map.containsKey("type")) throw new MissingPropertyException("___", "type");
        return (String) _map.get("type");
    }

    public FlutterTileWidgetParcel getChild() {
        return getNested("child");
    }

    public FlutterTileWidgetParcel getChildOrThrow() throws MissingPropertyException {
        return getNestedOrThrow("child");
    }

    public List<FlutterTileWidgetParcel> getChildren() {
        final List<Map<String, Object>> children = (List<Map<String, Object>>) _map.get(CommonTileProperties.Children);
        final List<FlutterTileWidgetParcel> result = new ArrayList<>();

        for (Map<String, Object> child : children) {
            result.add(new FlutterTileWidgetParcel(child));
        }

        return result;
    }

    public void throwIfPropertyNotFound(String prop) throws MissingPropertyException {
        if (!contains(prop)) throw new MissingPropertyException(getType(), prop);
    }

    public boolean contains(String prop) {
        return _map.containsKey(prop) && _map.get(prop) != null;
    }

    public boolean isValue(String prop, Object value) {
        if (!contains(prop)) return false;
        return _map.get(prop).equals(value);
    }

    public Object get(String prop) {
        return _map.get(prop);
    }

    public int getInt(String prop) {
        final Object v = _map.get(prop);

        if (v instanceof Long) return ((Long) v).intValue();
        return (int) v;
    }

    public int getIntOrThrow(String prop) throws MissingPropertyException {
        throwIfPropertyNotFound(prop);
        final Object v = _map.get(prop);

        if (v instanceof Long) return ((Long) v).intValue();
        return (int) v;
    }

    public int getIntOrDefault(String prop, int def) {
        if (!_map.containsKey(prop)) return def;

        return getInt(prop);
    }

    public boolean getBool(String prop) {
        final Object v = _map.get(prop);

        return (boolean) v;
    }

    public float getFloat(String prop) {
        final Object v = _map.get(prop);

        if (v instanceof Integer) return ((Integer) v).floatValue();
        if (v instanceof Double) return ((Double) v).floatValue();
        return (float) v;
    }

    public float getFloatOrThrow(String prop) throws MissingPropertyException {
        throwIfPropertyNotFound(prop);
        final Object v = _map.get(prop);

        if (v instanceof Integer) return ((Integer) v).floatValue();
        if (v instanceof Double) return ((Double) v).floatValue();
        return (float) v;
    }

    public String getString(String prop) {
        return _map.get(prop).toString();
    }

    public String getStringOrDefault(String prop, String defaultValue) {
        Object value = _map.getOrDefault(prop, null);
        if (value == null) return defaultValue;
        return value.toString();
    }

    public String getStringOrThrow(String prop) throws MissingPropertyException {
        throwIfPropertyNotFound(prop);
        return _map.get(prop).toString();
    }

    public FlutterTileWidgetParcel getNested(String prop) {
        return new FlutterTileWidgetParcel((Map<String, Object>) _map.get(prop));
    }

    public FlutterTileWidgetParcel getNestedOrThrow(String prop) throws MissingPropertyException {
        throwIfPropertyNotFound(prop);
        return new FlutterTileWidgetParcel((Map<String, Object>) _map.get(prop));
    }


}
