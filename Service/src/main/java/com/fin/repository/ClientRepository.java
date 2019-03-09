package com.fin.repository;

import com.fin.entity.Client;
import com.fin.security.Role;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("clientRepository")
public class ClientRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public Client authenticate(Client client) {
        String username = client.getUsername();
        String password = client.getPassword();
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "' and c.password='" + password + "'";
        return getElementOrNull(em.createQuery(query, Client.class).getResultList());
    }

    public boolean checkToken(String token) {
        Client client = findByToken(token);
        return client != null;
    }

    public Client findByToken(String token) {
        String query = "SELECT c FROM Client c WHERE c.token='" + token + "'";
        return getElementOrNull(em.createQuery(query, Client.class).getResultList());
    }

    public Client findByUsername(String username) {
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "'";
        return getElementOrNull(em.createQuery(query, Client.class).getResultList());
    }

    public boolean checkRoleByUsername(String username, List<Role> roles) {
        Client client = findByUsername(username);
        return roles.contains(client.getRole());
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
