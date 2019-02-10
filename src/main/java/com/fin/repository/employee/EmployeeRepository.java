package com.fin.repository.employee;

import com.fin.entity.Children;
import com.fin.entity.employee.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Named("employeeRepository")
public class EmployeeRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
        em = entityManagerFactory.createEntityManager();
    }

    public Employee create(Employee employee) {
        em.getTransaction().begin();
        em.persist(employee);
        switch (employee.getTypeEmployee()){
            case BABYSITTER:
                em.persist(new Babysitter(employee));
                break;
            case CHIEF:
                em.persist(new Chief(employee));
                break;
            case EDUCATOR:
                em.persist(new Educator(employee));
                break;
            case SECURITY:
                em.persist(new Security(employee));
                break;
            case TEACHER:
                em.persist(new Teacher(employee));
                break;
        }
        em.getTransaction().commit();
        return employee;
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