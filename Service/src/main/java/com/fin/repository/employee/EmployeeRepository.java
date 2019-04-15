package com.fin.repository.employee;

import com.fin.entity.Client;
import com.fin.entity.employee.Chief;
import com.fin.entity.employee.Employee;
import com.fin.repository.Repository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("employeeRepository")
public class EmployeeRepository extends Repository {

    public Employee findByClient(Client client) {
        String query = "SELECT e FROM Employee e WHERE e.client.id='" + client.getId() + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Employee.class).getResultList());
    }

    public Chief findChief(Employee employee) {
        String query = "SELECT c FROM Chief c WHERE c.id='" + employee.getId() + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Chief.class).getResultList());
    }

    public List<Employee> findEmployee(Employee employeeData) {
        String query = "SELECT * FROM Employee e JOIN Client c ON e.client_id=c.id WHERE";

        boolean flagAND = false;
        if (employeeData.getClient() != null && employeeData.getClient().getUsername() != null
                && !employeeData.getClient().getUsername().isEmpty()) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.username LIKE '%" + employeeData.getClient().getUsername() + "%'";
        }
        if (employeeData.getName() != null && !employeeData.getName().isEmpty()) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " e.name LIKE '%" + employeeData.getName() + "%'";
        }
        if (employeeData.getSurname() != null && !employeeData.getSurname().isEmpty()) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " e.surname LIKE '%" + employeeData.getSurname() + "%'";
        }
        if (employeeData.getPassport() != null && !employeeData.getPassport().isEmpty()) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " e.passport LIKE '%" + employeeData.getPassport() + "%'";
        }
        if (employeeData.getInn() != null && !employeeData.getInn().isEmpty()) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " e.passport LIKE '%" + employeeData.getPassport() + "%'";
        }
        if (employeeData.getPhoneNumber() != null && !employeeData.getPhoneNumber().isEmpty()) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " e.phoneNumber LIKE '%" + employeeData.getPhoneNumber() + "%'";
        }

        query += " AND e.dtype ILIKE '" + employeeData.getClient().getRole() + "'";

        return getEntityManager().createNativeQuery(query, Employee.class).getResultList();
    }

}
