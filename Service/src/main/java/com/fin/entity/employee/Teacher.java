package com.fin.entity.employee;

import com.fin.entity.Client;
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
@Table
@Setter
@Getter
public class Teacher extends Employee implements Serializable {
    public Teacher(Employee employee) {
        this.name = employee.getName();
        this.client = employee.getClient();
        this.id = employee.getId();
        this.inn = employee.getInn();
        this.passport = employee.getPassport();
        this.phone = employee.getPhone();
        this.salary = employee.getSalary();
        this.surname = employee.getSurname();
    }

    public Teacher() {
        this.typeEmployee = TypeEmployee.TEACHER;
    }

    public Teacher(String name, String surname, String phone, String passport, String inn, double salary, Client client, TypeEmployee typeEmployee) {
        super(name, surname, phone, passport, inn, salary, client, TypeEmployee.TEACHER);
    }
}
