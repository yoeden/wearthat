package yoeden.flutter.wear.communication.dtos;

import java.util.HashMap;

public class WearNode {
    private final String displayName;
    private final String id;
    private final boolean isNearby;

    public WearNode(String displayName, String id, boolean isNearby) {
        this.displayName = displayName;
        this.id = id;
        this.isNearby = isNearby;
    }

    public String getId() {return id;}

    public HashMap<String,Object> toMap()
    {
        HashMap<String,Object> map = new HashMap<>();
        map.put("displayName", displayName);
        map.put("id", id);
        map.put("isNearby", isNearby);

        return map;
    }
}
