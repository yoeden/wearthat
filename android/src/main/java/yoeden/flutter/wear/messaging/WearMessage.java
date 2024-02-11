package yoeden.flutter.wear.messaging;

import java.util.Map;

public class WearMessage {

    public static WearMessage fromJson(Map<String,Object> json) {
        return new WearMessage((String) json.get("path"), (byte[]) json.get("data"));
    }

    private final String _path;
    private final byte[] _data;

    public WearMessage(String path, byte[] data) {
        _path = path;
        _data = data;
    }

    public byte[] getData() {
        return _data;
    }

    public String getPath() {
        return _path;
    }
}
