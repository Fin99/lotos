package com.fin.repository;

import com.fin.entity.Children;
import com.fin.entity.Client;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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

    public List<Children> findChildren(Children childrenData) {
        String query = "SELECT c FROM Children c WHERE";


        boolean flagAND = false;
        if (childrenData.getClient() != null && childrenData.getClient().getUsername() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.client.username LIKE '%" + childrenData.getClient().getUsername() + "%'";
        }
        if (childrenData.getName() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.name LIKE '%" + childrenData.getName() + "%'";
        }
        if (childrenData.getSurname() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.surname LIKE '%" + childrenData.getSurname() + "%'";
        }
        if (childrenData.getMedicalBook().getSex() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.medicalBook.sex LIKE '%" + childrenData.getMedicalBook().getSex() + "%'";
        }

        return em.createQuery(query, Children.class).getResultList();
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
