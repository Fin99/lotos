package com.fin.entity.employee;

import com.fin.entity.security.Camera;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Security extends Employee implements Serializable {
    @ManyToMany
    @JoinTable(name = "security_camera")
    private Set<Camera> cameraSet;

    public Security(Employee employee) {
        this.name = employee.getName();
        this.client = employee.getClient();
        this.id = employee.getId();
        this.inn = employee.getInn();
        this.passport = employee.getPassport();
        this.phone = employee.getPhone();
        this.salary = employee.getSalary();
        this.surname = employee.getSurname();
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("salary", salary)
                .add("typeEmployee", "SECURITY");

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
