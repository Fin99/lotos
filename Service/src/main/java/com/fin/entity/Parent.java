package com.fin.entity;

import com.fin.entity.money.Wallet;
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
public class Parent implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "parent_id", sequenceName = "parent_id_seq", allocationSize = 1)
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
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("surname", surname)
                .add("sex", sex)
                .add("phoneNumber", phoneNumber)
                .add("wallet", wallet.toJson())
                .build();
    }

}
