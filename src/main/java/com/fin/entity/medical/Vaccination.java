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
public class Vaccination implements Serializable {
    @Id
    @SequenceGenerator(name = "vaccination_id", sequenceName = "vaccination_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccination_id")
    private long id;
}