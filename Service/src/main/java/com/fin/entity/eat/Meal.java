package com.fin.entity.eat;

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
public class Meal implements Serializable {
    @Id
    @SequenceGenerator(name = "meal_id", sequenceName = "meal_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_id")
    private long id;
    @Column
    private String first;
    @Column
    private String second;
    @Column
    private String third;
    @Column
    private String dessert;
    @Column
    private String drink;
    @Column
    private boolean allergenic;
}
