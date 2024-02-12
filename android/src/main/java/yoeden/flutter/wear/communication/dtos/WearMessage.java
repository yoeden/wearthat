package yoeden.flutter.wear.communication.dtos;

import java.util.Map;

public class WearMessage {
    private final String path;
    private final byte[] data;
    private final String node;
    private final int id;

    public WearMessage(String path, byte[] data, String node, int id) {
        this.path = path;
        this.data = data;
        this.node = node;
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public String getNode() {
        return node;
    }

    public int getId() {
        return id;
    }
}
