package com.fin.entity.game;

import com.fin.entity.Jsonable;
import lombok.Getter;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

@Getter
@Setter
public class Hit implements Jsonable {
    private Fight fight;
    private HitDirection hitDirection;
    private boolean isCritical;
    private boolean isBlocked;
    private double damage;
    private double time;

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (hitDirection != null) {
            builder.add("hitDirection", hitDirection.toString());
        }
        builder.add("isCritical", isCritical);
        builder.add("isBlocked", isBlocked);
        builder.add("damage", damage);
        builder.add("time", time);
        return builder.build();
    }
}
