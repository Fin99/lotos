package com.fin.entity.medical;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Allergy implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "allergy_id", sequenceName = "allergy_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allergy_id")
    private long id;
    @Column
    private String name;

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);

        if (name != null) {
            builder.add("name", name);
        }

        return builder.build();
    }
}
