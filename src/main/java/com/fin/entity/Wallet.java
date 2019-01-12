package com.fin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double account;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("account", this.account)
                .build();
    }

}
