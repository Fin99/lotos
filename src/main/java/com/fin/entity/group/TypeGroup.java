package com.fin.entity.group;

import com.fin.entity.Jsonable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "type_group")
@Setter
@Getter
@NoArgsConstructor
public class TypeGroup implements Serializable, Jsonable {
    @Id
    @SequenceGenerator(name = "type_group_id", sequenceName = "type_group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_group_id")
    private long id;
    @Column
    private String name;
    @Column(name = "number_group")
    private int numberGroup;
    @Column(name = "max_number_group")
    private int maxNumberGroup;
    @Column(name = "max_number_children")
    private int maxNumberChildren;
    @Column
    private boolean speech;
    @Column
    private boolean intellectual;

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
               // .add("numberGroup", numberGroup)
               // .add("maxNumberGroup", maxNumberGroup)
               // .add("maxNumberChildren", maxNumberChildren)
                .add("speech", speech)
                .add("intellectual", intellectual);

        if (name != null) {
            builder.add("name", name);
        }

        return builder.build();
    }
}
