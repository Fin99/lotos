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
@Named("clientRepository")
public class ClientRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
        em = entityManagerFactory.createEntityManager();
    }


    public boolean isExist(String username) {
        em.getTransaction().begin();
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "'";
        Client client = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        em.getTransaction().commit();
        return client != null;
    }

    public Client authenticate(Client client) {
        em.getTransaction().begin();
        String username = client.getUsername();
        String password = client.getPassword();
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "' and c.password='" + password + "'";
        Client authClient = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        em.getTransaction().commit();
        return authClient;
    }

    private Client getClientOrNull(List<Client> resultList) {
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public Client create(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
        return client;
    }

    public void update(Client client) {
        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();
    }

    public boolean checkToken(String token) {
        Client client = findByToken(token);
        return client != null;
    }

    public Client findByToken(String token) {
        em.getTransaction().begin();
        String query = "SELECT c FROM Client c WHERE c.token='" + token + "'";
        Client client = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        em.getTransaction().commit();
        return client;
    }

    public boolean checkRoleByUsername(String username, List<Role> roles) {
        em.getTransaction().begin();
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "'";
        Client client = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        boolean containsRole = client.getRoles().containsAll(roles);
        em.getTransaction().commit();
        return containsRole;
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
