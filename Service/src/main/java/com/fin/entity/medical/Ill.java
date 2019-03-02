package com.fin.entity.medical;

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
public class Ill  implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "ill_id", sequenceName = "ill_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ill_id")
    private long id;
    @Column
    private String name;

}
