package com.fin.entity.place;

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
public class Place implements Serializable {
    @Id
    @SequenceGenerator(name = "place_id", sequenceName = "place_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id")
    private long id;
    @Column
    private String name;
}
