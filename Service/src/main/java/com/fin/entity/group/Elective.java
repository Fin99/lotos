package com.fin.entity.group;

import com.fin.entity.Children;
import com.fin.entity.employee.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Elective implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "elective_id", sequenceName = "elective_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elective_id")
    private long id;
    @Column
    private String name;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany
    @JoinTable(name = "chidlren_elective")
    private Set<Children> childrenSet;
}
