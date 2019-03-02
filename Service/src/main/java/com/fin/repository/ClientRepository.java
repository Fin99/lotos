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
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public Client authenticate(Client client) {
        String username = client.getUsername();
        String password = client.getPassword();
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "' and c.password='" + password + "'";
        Client authClient = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        return authClient;
    }

    public void create(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
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
        String query = "SELECT c FROM Client c WHERE c.token='" + token + "'";
        Client client = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        return client;
    }

    public Client findByUsername(String username) {
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "'";
        Client client = getClientOrNull(em.createQuery(query, Client.class).getResultList());
        return client;
    }

    public boolean checkRoleByUsername(String username, List<Role> roles) {
        Client client = findByUsername(username);
        boolean containsRole = roles.contains(client.getRole());
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

    private Client getClientOrNull(List<Client> resultList) {
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
