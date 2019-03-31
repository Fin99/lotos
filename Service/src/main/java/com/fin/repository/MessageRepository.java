package com.fin.repository;

import com.fin.entity.Client;
import com.fin.entity.Message;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Singleton
@Named("messageRepository")
public class MessageRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public List<Message> findAllMessage(Client client1, Client client2) {
        String query = "SELECT m FROM Message m WHERE " +
                "m.sender.id='" + client1.getId() + "' AND m.receiver.id='" + client2.getId() + "' OR " +
                "m.sender.id='" + client2.getId() + "' AND m.receiver.id='" + client1.getId() + "'";
        return em.createQuery(query, Message.class).getResultList();
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
