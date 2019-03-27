package com.fin.repository;

import com.fin.entity.Client;
import com.fin.entity.Parent;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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

    public List<Parent> findParents(Parent parentData) {
        String query = "SELECT p FROM Parent p WHERE ";

        if (parentData.getClient() != null && parentData.getClient().getUsername() != null) {
            query += "p.client.id LIKE " + parentData.getClient().getUsername() + " ";
        }
        if (parentData.getName() != null) {
            query += "p.name LIKE " + parentData.getName() + " ";
        }
        if (parentData.getSurname() != null) {
            query += "p.surname LIKE " + parentData.getSurname() + " ";
        }
        if (parentData.getSex() != null) {
            query += "p.sex LIKE " + parentData.getSex() + " ";
        }
        if (parentData.getPhoneNumber() != null) {
            query += "p.phoneNumber LIKE " + parentData.getPhoneNumber();
        }
        query += ";";

        return em.createQuery(query, Parent.class).getResultList();
    }
}
