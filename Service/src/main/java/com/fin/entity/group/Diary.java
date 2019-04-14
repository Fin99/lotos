package com.fin.entity.group;

import com.fin.entity.Child;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Diary implements Serializable {
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
}
