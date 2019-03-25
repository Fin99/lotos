package com.fin.entity.eat;

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
public class Supplier implements Serializable {
    @Id
    @SequenceGenerator(name = "supplier_id", sequenceName = "supplier_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_id")
    private long id;
    @Column
    private String name;
    @Column
    private String phone;
}
