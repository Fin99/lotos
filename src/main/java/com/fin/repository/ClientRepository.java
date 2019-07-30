package com.fin.repository;

import com.fin.entity.Client;
import com.fin.security.Role;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("clientRepository")
public class ClientRepository extends Repository {

    public Client authenticate(Client client) {
        String username = client.getUsername();
        String password = client.getPassword();
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "' and c.password='" + password + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Client.class).getResultList());
    }

    public boolean checkToken(String token) {
        Client client = findByToken(token);
        return client != null;
    }

    public Client findByToken(String token) {
        String query = "SELECT c FROM Client c WHERE c.token='" + token + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Client.class).getResultList());
    }

    public Client findByUsername(String username) {
        String query = "SELECT c FROM Client c WHERE c.username='" + username + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Client.class).getResultList());
    }

    public boolean checkRoleByUsername(String username, List<Role> roles) {
        Client client = findByUsername(username);
        return roles.contains(client.getRole());
    }

}
