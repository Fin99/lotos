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

@Singleton
@Named("employeeRepository")
public class EmployeeRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public <T extends Employee> T createEmployee(T employee) {
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        return employee;
    }

    public Employee findByClient(Client client) {
        String query = "SELECT e FROM Employee e WHERE e.client.id='" + client.getId() + "'";
        return getEntityOrNull(em.createQuery(query, Employee.class).getResultList());
    }

    public Chief findChief(Employee employee) {
        String query = "SELECT c FROM Chief c WHERE c.id='" + employee.getId() + "'";
        return getEntityOrNull(em.createQuery(query, Chief.class).getResultList());
    }

    private <T> T getEntityOrNull(List<T> resultList) {
        return resultList.isEmpty() ? null : resultList.get(0);
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
