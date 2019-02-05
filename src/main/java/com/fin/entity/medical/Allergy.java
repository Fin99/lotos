package com.fin.entity.medical;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Allergy implements Serializable {
    @Id
    @SequenceGenerator(name = "allergy_id", sequenceName = "allergy_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allergy_id")
    private long id;
    @Column
    private String name;
}
