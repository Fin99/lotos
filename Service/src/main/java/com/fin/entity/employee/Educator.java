package com.fin.entity.employee;

import com.fin.entity.Client;
import com.fin.entity.group.GradeBook;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Setter
@Getter
public class Educator extends Employee implements Serializable {
    @OneToMany(mappedBy = "educator", cascade = CascadeType.ALL)
    private List<GradeBook> gradeBookList;

    public Educator(Employee employee) {
        this.name = employee.getName();
        this.client = employee.getClient();
        this.id = employee.getId();
        this.inn = employee.getInn();
        this.passport = employee.getPassport();
        this.phone = employee.getPhone();
        this.salary = employee.getSalary();
        this.surname = employee.getSurname();
        this.gradeBookList = new ArrayList<>();
    }

    public Educator() {
        this.typeEmployee = TypeEmployee.EDUCATOR;
    }

    public Educator(String name, String surname, String phone, String passport, String inn, double salary, Client client, TypeEmployee typeEmployee) {
        super(name, surname, phone, passport, inn, salary, client, TypeEmployee.EDUCATOR);
    }
}