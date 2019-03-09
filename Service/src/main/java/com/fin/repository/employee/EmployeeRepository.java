package com.fin.repository.employee;

import com.fin.entity.Client;
import com.fin.entity.employee.Chief;
import com.fin.entity.employee.Employee;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("employeeRepository")
public class EmployeeRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public Employee findByClient(Client client) {
        String query = "SELECT e FROM Employee e WHERE e.client.id='" + client.getId() + "'";
        return getElementOrNull(em.createQuery(query, Employee.class).getResultList());
    }

    public Chief findChief(Employee employee) {
        String query = "SELECT c FROM Chief c WHERE c.id='" + employee.getId() + "'";
        return getElementOrNull(em.createQuery(query, Chief.class).getResultList());
    }

    @PreDestroy
    public void preDestroy() {
        if (entityManagerFactory.isOpen() && entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        if (em.isOpen() && em != null) {
            em.close();
        }
    }
}
