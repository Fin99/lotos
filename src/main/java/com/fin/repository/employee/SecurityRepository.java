package com.fin.repository.employee;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Named("securityRepository")
public class SecurityRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
        em = entityManagerFactory.createEntityManager();
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
