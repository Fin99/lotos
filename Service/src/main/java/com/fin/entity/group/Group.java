package com.fin.entity.group;

import com.fin.entity.employee.Babysitter;
import com.fin.entity.employee.Educator;
import com.fin.entity.employee.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Group implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "group_id", sequenceName = "group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id")
    private long id;
    @Column
    private String name;
    @ManyToOne
    private TypeGroup typeGroup;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Babysitter babysitter;
    @ManyToOne
    private Educator educator1;
    @ManyToOne
    private Educator educator2;
}
