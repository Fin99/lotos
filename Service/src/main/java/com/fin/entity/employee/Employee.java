package com.fin.entity.employee;

import com.fin.entity.Client;
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
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(name = "employee_id", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id")
    protected long id;
    @Column
    protected String phone;
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
    @OneToOne
    protected Client client;

    @Transient
    protected TypeEmployee typeEmployee;

    public enum TypeEmployee{
        BABYSITTER,
        CHIEF,
        EDUCATOR,
        SECURITY,
        TEACHER
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
        if (phone != null) {
            builder.add("phone", phone);
        }
        if (client != null) {
            builder.add("client", client.toJson());
        }

        return builder.build();
    }

}
