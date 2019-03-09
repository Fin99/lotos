package com.fin.repository;

import com.fin.entity.Children;
import com.fin.entity.Client;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("childrenRepository")
public class ChildrenRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public Children findByClient(Client client) {
        String query = "SELECT c FROM Children c WHERE c.client.id='" + client.getId() + "'";
        return getElementOrNull(em.createQuery(query, Children.class).getResultList());
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
