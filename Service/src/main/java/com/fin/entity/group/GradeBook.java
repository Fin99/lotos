package com.fin.entity.group;

import com.fin.entity.Children;
import com.fin.entity.employee.Educator;
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
public class GradeBook implements Serializable {
    @Id
    @SequenceGenerator(name = "grade_book_id", sequenceName = "grade_book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_book_id")
    private long id;
    @ManyToOne
    private Educator educator;
    @ManyToOne
    private Children children;
    @Column
    private boolean attend;
    @Column
    private int eatingScore;
    @Column
    private int behavior;
    @Column
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private Date date;
}
