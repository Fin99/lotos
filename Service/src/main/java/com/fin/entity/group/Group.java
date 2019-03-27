package com.fin.entity.group;

import com.fin.entity.employee.Babysitter;
import com.fin.entity.employee.Educator;
import com.fin.entity.employee.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "\"group\"")
@Setter
@Getter
@NoArgsConstructor
public class Group implements Serializable {
    @Id
    @SequenceGenerator(name = "group_id", sequenceName = "group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id")
    private long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "type_group_id")
    private TypeGroup typeGroup;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Babysitter babysitter;
    @ManyToOne
    private Educator educator1;
    @ManyToOne
    private Educator educator2;

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);

        if (name != null) {
            builder.add("name", name);
        }
        if (typeGroup != null) {
            builder.add("typeGroup", typeGroup.toJson());
        }
        if (teacher != null) {
            builder.add("teacher", teacher.toJson());
        }
        if (babysitter != null) {
            builder.add("babysitter", babysitter.toJson());
        }
        if (educator1 != null) {
            builder.add("educator1", educator1.toJson());
        }
        if (educator2 != null) {
            builder.add("educator2", educator2.toJson());
        }

        return builder.build();
    }
}
