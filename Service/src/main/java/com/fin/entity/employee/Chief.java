package com.fin.entity.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Chief extends Employee implements Serializable {
    public Chief(Employee employee) {
        this.name = employee.getName();
        this.client = employee.getClient();
        this.id = employee.getId();
        this.inn = employee.getInn();
        this.passport = employee.getPassport();
        this.phone = employee.getPhone();
        this.salary = employee.getSalary();
        this.surname = employee.getSurname();
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
                .add("typeEmployee", "CHIEF")
                .build();
    }
}
