package com.fin.entity;

import com.fin.entity.money.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Parent implements Serializable {
    @Id
    @SequenceGenerator(name = "parent_id", sequenceName = "parent_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_id")
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private char sex;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToOne
    private Client client;
    @OneToOne
    private Wallet wallet;

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("sex", sex);

        if (name != null) {
            builder.add("name", name);
        }
        if (surname != null) {
            builder.add("surname", surname);
        }
        if (phoneNumber != null) {
            builder.add("phoneNumber", phoneNumber);
        }
        if (wallet != null) {
            builder.add("wallet", wallet.toJson());
        }
        if (client != null) {
            builder.add("client", client.toJson());
        }

        return builder.build();
    }

}
