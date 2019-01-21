package com.fin.repository;

import com.fin.entity.Client;
import com.fin.entity.Parent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Singleton
@Named("parentRepository")
public class ParentRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
        em = entityManagerFactory.createEntityManager();
    }

    public Parent create(Parent parent) {
        em.getTransaction().begin();
        em.persist(parent);
        em.getTransaction().commit();
        return parent;
    }

    public Parent findByClient(Client client) {
        em.getTransaction().begin();
        String query = "SELECT p FROM Parent p WHERE p.client.id='" + client.getId() + "'";
        Parent parent = getParentOrNull(em.createQuery(query, Parent.class).getResultList());
        em.getTransaction().commit();
        return parent;
    }

    private Parent getParentOrNull(List<Parent> resultList) {
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
