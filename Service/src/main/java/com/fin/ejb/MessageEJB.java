package com.fin.ejb;

import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.Message;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.MessageRepository;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;

@Getter
@Setter
@Stateless
public class MessageEJB {

    @Inject
    private MainRepository mainRepository;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private MessageRepository messageRepository;

    public JsonObject isUnreadMessagesExist(Client receiver, Client sender) {
        List<Message> unreadMessages = messageRepository.findAllUnreadMessages(receiver, sender);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("unreadExist", unreadMessages.size() > 0);
        return builder.build();
    }

}
