package com.fin.repository;

import com.fin.entity.Child;
import com.fin.entity.Client;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("childRepository")
public class ChildRepository extends Repository {

    public Child findByClient(Client client) {
        String query = "SELECT c FROM Child c WHERE c.client.id='" + client.getId() + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Child.class).getResultList());
    }

    public List<Child> findChildren(Child childData) {
        String query = "SELECT c FROM Child c WHERE";


        boolean flagAND = false;
        if (childData.getClient() != null && childData.getClient().getUsername() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.client.username LIKE '%" + childData.getClient().getUsername() + "%'";
        }
        if (childData.getName() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.name LIKE '%" + childData.getName() + "%'";
        }
        if (childData.getSurname() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.surname LIKE '%" + childData.getSurname() + "%'";
        }
        if (childData.getMedicalBook() != null && childData.getMedicalBook().getSex() != null) {
            if (flagAND) {
                query += " AND";
            } else {
                flagAND = true;
            }
            query += " c.medicalBook.sex LIKE '%" + childData.getMedicalBook().getSex() + "%'";
        }

        return getEntityManager().createQuery(query, Child.class).getResultList();
    }

    public List<Child> findAllChildren() {
        String query = "SELECT c FROM Child c";
        return getEntityManager().createQuery(query, Child.class).getResultList();
    }
}
