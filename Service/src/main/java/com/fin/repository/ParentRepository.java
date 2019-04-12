package com.fin.repository;

import com.fin.entity.Client;
import com.fin.entity.Parent;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("parentRepository")
public class ParentRepository extends Repository {

    public Parent findByClient(Client client) {
        String query = "SELECT p FROM Parent p WHERE p.client.id='" + client.getId() + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Parent.class).getResultList());
    }

    public List<Parent> findParents(Parent parentData) {
        String query = "SELECT p FROM Parent p WHERE";


        boolean flagAND = false;
        if (parentData.getClient() != null && parentData.getClient().getUsername() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " p.client.username LIKE '%" + parentData.getClient().getUsername() + "%'";
        }
        if (parentData.getName() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " p.name LIKE '%" + parentData.getName() + "%'";
        }
        if (parentData.getSurname() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " p.surname LIKE '%" + parentData.getSurname() + "%'";
        }
        if (parentData.getSex() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " p.sex LIKE '%" + parentData.getSex() + "%'";
        }
        if (parentData.getPhoneNumber() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " p.phoneNumber LIKE '%" + parentData.getPhoneNumber() + "%'";
        }

        return getEntityManager().createQuery(query, Parent.class).getResultList();
    }
}
