package com.fin.entity;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public interface Jsonable {
    JsonObject toJson();

    static <T extends Jsonable> JsonArray wrapList(List<T> entities) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (T entity : entities) {
            jsonArrayBuilder.add(entity.toJson());
        }

        return jsonArrayBuilder.build();
    }
}
