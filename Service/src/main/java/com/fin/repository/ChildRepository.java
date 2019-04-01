package com.fin.repository;

import com.fin.entity.Children;
import com.fin.entity.Client;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("childRepository")
public class ChildRepository extends Repository {

    public Children findByClient(Client client) {
        String query = "SELECT c FROM Children c WHERE c.client.id='" + client.getId() + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Children.class).getResultList());
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
        if (childrenData.getMedicalBook() != null && childrenData.getMedicalBook().getSex() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.medicalBook.sex LIKE '%" + childrenData.getMedicalBook().getSex() + "%'";
        }

        return getEntityManager().createQuery(query, Children.class).getResultList();
    }

    public List<Children> findAllChildren() {
        String query = "SELECT c FROM Children c";
        return getEntityManager().createQuery(query, Children.class).getResultList();
    }
}
