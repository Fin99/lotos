package com.fin.entity.place;

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
public class Place implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "place_id", sequenceName = "place_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id")
    private long id;
    @Column
    private String name;

    public JsonObject toJson() {
        System.out.println(id);
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .build();
    }
}
