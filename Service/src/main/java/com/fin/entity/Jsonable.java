package com.fin.entity;

import javax.json.JsonObject;

public interface Jsonable {
    JsonObject toJson();
}
