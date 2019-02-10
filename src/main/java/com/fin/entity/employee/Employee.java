package com.fin.entity.employee;

import com.fin.entity.Client;
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
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(name = "employee_id", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id")
    private long id;
    @Column
    private String phone;
    @Column
    private String passport;
    @Column
    private String inn;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String position;
    @Column
    private double salary;
    @OneToOne
    private Client client;

    private TypeEmployee typeEmployee;

    public enum TypeEmployee{
        BABYSITTER,
        CHIEF,
        EDUCATOR,
        SECURITY,
        TEACHER
    }
}
