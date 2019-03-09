package com.fin.repository;

import com.fin.entity.Client;
import com.fin.entity.Parent;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("parentRepository")
public class ParentRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public Parent findByClient(Client client) {
        String query = "SELECT p FROM Parent p WHERE p.client.id='" + client.getId() + "'";
        return getElementOrNull(em.createQuery(query, Parent.class).getResultList());
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
