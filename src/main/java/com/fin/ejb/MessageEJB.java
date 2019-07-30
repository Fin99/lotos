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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public JsonArray getUnreadMessages(Client receiver, Client sender) {
        List<Message> unreadMessages = messageRepository.findAllUnreadMessages(receiver, sender);
        return Jsonable.wrapList(unreadMessages);
    }

    public void readMessages(Client receiver, List<Message> messagesWithId) {
        List<Message> unreadMessages = messageRepository.findAllUnreadMessages(receiver);
        Set<Long> messagesId = messagesWithId.stream().map(Message::getId).collect(Collectors.toSet());
        List<Message> readMessages = unreadMessages.stream().filter(message -> messagesId.contains(message.getId()))
                .peek(message -> message.setRead(true)).collect(Collectors.toList());

        readMessages.forEach(message -> mainRepository.update(message));
    }

}
