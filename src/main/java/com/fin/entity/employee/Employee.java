package com.fin.entity.employee;

import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@NoArgsConstructor
public class Employee implements Serializable, Jsonable {
    @Id
    @SequenceGenerator(name = "employee_id", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id")
    protected long id;
    @Column(name = "phone_number")
    protected String phoneNumber;
    @Column
    protected String passport;
    @Column
    protected String inn;
    @Column
    protected String name;
    @Column
    protected String surname;
    @Column
    protected double salary;
    @OneToOne(cascade = CascadeType.ALL)
    protected Client client;
    @Transient
    protected TypeEmployee typeEmployee;

    public Employee(String name, String surname, String phoneNumber, String passport, String inn, double salary, Client client, TypeEmployee typeEmployee) {
        this.phoneNumber = phoneNumber;
        this.passport = passport;
        this.inn = inn;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.client = client;
        this.typeEmployee = typeEmployee;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("salary", salary);

        if (name != null) {
            builder.add("name", name);
        }
        if (surname != null) {
            builder.add("surname", surname);
        }
        if (inn != null) {
            builder.add("inn", inn);
        }
        if (passport != null) {
            builder.add("passport", passport);
        }
        if (phoneNumber != null) {
            builder.add("phoneNumber", phoneNumber);
        }
        if (client != null) {
            builder.add("client", client.toJson());
        }
        if (typeEmployee != null) {
            builder.add("typeEmployee", typeEmployee.name());
        }

        return builder.build();
    }

    public enum TypeEmployee {
        BABYSITTER,
        CHIEF,
        EDUCATOR,
        SECURITY,
        TEACHER,
        COOKER,
        DOCTOR
    }

}
