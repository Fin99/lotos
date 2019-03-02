package com.fin.entity.employee;

import com.fin.entity.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "employee_id", sequenceName = "employee_id_seq", allocationSize = 1)
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
    private double salary;
    @OneToOne
    private Client client;

    @Transient
    private TypeEmployee typeEmployee;

    public enum TypeEmployee{
        BABYSITTER,
        CHIEF,
        EDUCATOR,
        SECURITY,
        TEACHER
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("phone", phone)
                .add("passport", passport)
                .add("inn", inn)
                .add("name", name)
                .add("surname", surname)
                .add("salary", salary)
                .add("typeEmployee", typeEmployee.toString())
                .build();
    }

}
