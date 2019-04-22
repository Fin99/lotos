package com.fin.entity.group;

import com.fin.entity.Child;
import com.fin.entity.Jsonable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Diary implements Serializable, Jsonable {
    @Id
    @SequenceGenerator(name = "diary_id", sequenceName = "diary_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diary_id")
    private long id;
    @ManyToOne
    private Elective elective;
    @ManyToOne
    private Child child;
    @Column
    private int assessment;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private Date date;

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", id);
        builder.add("assessment", assessment);
        if (child != null) {
            builder.add("child", child.getEssentialsAsJson());
        }
        if (date != null) {
            builder.add("date", date.toString());
        }
        return builder.build();
    }
}
