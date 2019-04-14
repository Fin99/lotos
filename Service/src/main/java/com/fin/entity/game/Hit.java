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
    private Fight fight = null;
    private HitDirection hitDirection = HitDirection.TO_DIRE;
    private boolean isCritical = false;
    private boolean isBlocked = false;
    private double damage = 0.0;
    private double time = 0.0;

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
