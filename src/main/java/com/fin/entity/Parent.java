package com.fin.entity;

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
public class Parent implements Serializable {
    @Id
    @SequenceGenerator(name = "parent_id", sequenceName = "parent_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_id")
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private char sex;
    @Column
    private String phone_number;
    @OneToOne
    private Client client;
    @OneToOne
    private Wallet wallet;
}
