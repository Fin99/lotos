package com.fin.entity.money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Wallet implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "wallet_id", sequenceName = "wallet_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_id")
    private long id;

    @Column
    private double account;

    public Wallet(double account) {
        this.account = account;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("account", this.account)
                .build();
    }

}
