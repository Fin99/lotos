package com.fin.entity.group;

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
public class TypeGroup implements Serializable {
    @Id
    @SequenceGenerator(name = "type_group_id", sequenceName = "type_group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_group_id")
    private long id;
    @Column
    private String name;
    @Column
    private int numberGroup;
    @Column
    private int maxNumberGroup;
    @Column
    private int maxNumberChildren;
    @Column
    private boolean speech;
    @Column
    private boolean intellectual;

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("numberGroup", numberGroup)
                .add("maxNumberGroup", maxNumberGroup)
                .add("maxNumberChildren", maxNumberChildren)
                .add("speech", speech)
                .add("intellectual", intellectual);

        if (name != null) {
            builder.add("name", name);
        }

        return builder.build();
    }
}
