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
@Table
@Setter
@Getter
@NoArgsConstructor
public class Ill implements Serializable {
    @Id
    @SequenceGenerator(name = "ill_id", sequenceName = "ill_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ill_id")
    private long id;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "disease_strength")
    private DiseaseStrength diseaseStrength;

    public Ill(String name, DiseaseStrength diseaseStrength) {
        this.name = name;
        this.diseaseStrength = diseaseStrength;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);

        if (name != null) {
            builder.add("name", name);
        }

        return builder.build();
    }
}
