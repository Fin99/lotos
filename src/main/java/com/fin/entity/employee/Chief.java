package com.fin.entity.employee;

import com.fin.entity.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
public class Chief extends Employee implements Serializable {
    public Chief(Employee employee) {
        this.name = employee.getName();
        this.client = employee.getClient();
        this.id = employee.getId();
        this.inn = employee.getInn();
        this.passport = employee.getPassport();
        this.phoneNumber = employee.getPhoneNumber();
        this.salary = employee.getSalary();
        this.surname = employee.getSurname();
    }

    public Chief() {
        this.typeEmployee = TypeEmployee.CHIEF;
    }

    public Chief(String name, String surname, String phone, String passport, String inn, double salary, Client client, TypeEmployee typeEmployee) {
        super(name, surname, phone, passport, inn, salary, client, TypeEmployee.CHIEF);
    }
}
