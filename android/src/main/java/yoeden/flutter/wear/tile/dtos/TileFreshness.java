package yoeden.flutter.wear.tile.dtos;

import java.util.Map;

public class TileFreshness {
    public static TileFreshness fromJson(Map<String, Object> json) {
        Long until = null;
        Long after = null;

        if (json.containsKey("until")) {
            Object value = json.get("until");
            if (value instanceof Integer) until = ((Integer) value).longValue();
            if (value instanceof Long) until = (Long) value;
        }

        if (json.containsKey("interval")) {
            Object value = json.get("interval");
            if (value instanceof Integer) after = ((Integer) value).longValue();
            if (value instanceof Long) after = (Long) value;
        }

        return new TileFreshness(
                until,
                after
        );
    }

    public static TileFreshness never() {
        return new TileFreshness();
    }

    private final Long _until;
    private final Long _interval;

    public TileFreshness(Long until, Long after) {
        _until = until;
        _interval = after;
    }

    private TileFreshness() {
        _until = null;
        _interval = null;
    }

    public Long getUntil() {
        return _until;
    }

    public Long getInterval() {
        return _interval != null ? _interval : 0;
    }
}
