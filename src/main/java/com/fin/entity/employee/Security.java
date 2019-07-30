package com.fin.entity.employee;

import com.fin.entity.Client;
import com.fin.entity.security.Camera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
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
        this.phoneNumber = employee.getPhoneNumber();
        this.salary = employee.getSalary();
        this.surname = employee.getSurname();
    }

    public Security() {
        this.typeEmployee = TypeEmployee.SECURITY;
    }

    public Security(String name, String surname, String phone, String passport, String inn, double salary, Client client, TypeEmployee typeEmployee) {
        super(name, surname, phone, passport, inn, salary, client, TypeEmployee.SECURITY);
    }
}
