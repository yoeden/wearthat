package yoeden.flutter.wear.tiles.resources;

import java.util.Map;

public class TileResource {
    public static TileResource fromJson(Map<String,Object> map)
    {
      return new TileResource((String) map.get("id"), (byte[]) map.get("data"));
    }

    private final String id;
    private final byte[] data;

    public TileResource(String id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }
}
