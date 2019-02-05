package com.fin.repository;

import com.fin.entity.Client;
import com.fin.security.Role;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Singleton
@Named("childrenRepository")
public class ChildrenRepository {
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
