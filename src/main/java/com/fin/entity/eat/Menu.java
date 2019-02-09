package com.fin.entity.eat;

import com.fin.entity.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Menu implements Serializable {
    @Id
    @SequenceGenerator(name = "menu_id", sequenceName = "menu_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id")
    private long id;
    @ManyToOne
    private Meal breakfast;
    @ManyToOne
    private Meal lunch;
    @ManyToOne
    private Meal snack;
    @ManyToOne
    private Meal dinner;
    @ManyToMany
    @JoinTable(name = "group_menu")
    private Set<Group> groupSet;
}
