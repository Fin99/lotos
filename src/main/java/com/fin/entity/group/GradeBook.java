package com.fin.entity.group;

import com.fin.entity.Child;
import com.fin.entity.Jsonable;
import com.fin.entity.employee.Educator;
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
@Table(name = "grade_book")
@Setter
@Getter
@NoArgsConstructor
public class GradeBook implements Serializable, Jsonable {
    @Id
    @SequenceGenerator(name = "grade_book_id", sequenceName = "grade_book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_book_id")
    private long id;
    @ManyToOne
    private Educator educator;
    @ManyToOne
    private Child child;
    @Column
    private boolean attend;
    @Column(name = "eating_score")
    private int eatingScore;
    @Column
    private int behavior;
    @Column
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private Date date;

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", id);
        if (child != null) {
            builder.add("child", child.getEssentialsAsJson());
        }
        builder.add("attend", attend);
        builder.add("eatingScore", eatingScore);
        builder.add("behavior", behavior);
        if (note != null) {
            builder.add("note", note);
        }
        if (date != null) {
            builder.add("date", date.toString());
        }
        return builder.build();
    }
}
