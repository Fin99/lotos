package com.fin.entity.group;

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
public class TypeGroup implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "type_group_id", sequenceName = "type_group_id_seq", allocationSize = 1)
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
}
