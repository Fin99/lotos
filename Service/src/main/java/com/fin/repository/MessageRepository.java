package com.fin.repository;

import com.fin.entity.Client;
import com.fin.entity.Message;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("messageRepository")
public class MessageRepository extends Repository {

    public List<Message> findAllMessage(Client client1, Client client2) {
        String query = "SELECT m FROM Message m WHERE " +
                "m.sender.id='" + client1.getId() + "' AND m.receiver.id='" + client2.getId() + "' OR " +
                "m.sender.id='" + client2.getId() + "' AND m.receiver.id='" + client1.getId() + "'";
        return getEntityManager().createQuery(query, Message.class).getResultList();
    }

    public List<Message> findAllUnreadMessages(Client receiver, Client sender) {
        String query = "SELECT m FROM Message m WHERE " +
                "m.sender.id='" + sender.getId() + "' AND m.receiver.id='" + receiver.getId() + "' AND " +
                "m.read=false";
        return getEntityManager().createQuery(query, Message.class).getResultList();
    }
}
