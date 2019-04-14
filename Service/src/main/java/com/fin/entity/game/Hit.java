package com.fin.entity.game;

import com.fin.entity.Jsonable;
import lombok.Getter;
import lombok.Setter;

import javax.json.JsonObject;

@Getter
@Setter
public class Hit implements Jsonable {
    private Fight fight;
    private HitDirection hitDirection;
    private boolean isCritical;
    private boolean isBlocked;
    private double damage;

    @Override
    public JsonObject toJson() {
        return null;
    }
}
