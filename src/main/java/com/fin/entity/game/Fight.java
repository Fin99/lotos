package com.fin.entity.game;

import com.fin.entity.Jsonable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Fight implements Jsonable {
    private Fighter radiant;
    private Fighter dire;
    private List<Hit> hitList;
    private Date startDate;

    public Fight(Fighter radiant, Fighter dire) {
        this.radiant = radiant;
        this.dire = dire;
        this.hitList = new ArrayList<>();
        this.startDate = new Date();
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (radiant != null) {
            builder.add("radiant", radiant.toJson());
        }
        if (dire != null) {
            builder.add("dire", dire.toJson());
        }
        builder.add("hitList", Jsonable.wrapList(hitList));

        return builder.build();
    }
}
