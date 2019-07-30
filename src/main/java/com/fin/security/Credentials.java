package com.fin.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials implements Serializable {

    private String username;
    private String password;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("username", username)
                .add("password", password)
                .build();
    }
}