package com.fin.controller;


import com.fin.ejb.MessageEJB;
import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.Message;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.MessageRepository;
import com.fin.security.Secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/message")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageController {
    @Context
    SecurityContext securityContext;

    @Inject
    ClientRepository clientRepository;
    @Inject
    MessageRepository messageRepository;
    @Inject
    MainRepository mainRepository;

    @EJB
    private MessageEJB messageEJB;

    @POST
    @Path("/get")
    public Response getMessageWithClient(Client clientId) {
        Client client1 = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Client client2 = mainRepository.find(Client.class, clientId.getId());
        if (client1 == null || client2 == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Message> messages = messageRepository.findAllMessage(client1, client2);

        return Response.ok(Jsonable.wrapList(messages)).build();
    }

    @GET
    @Path("/is-unread-exist")
    public Response isUnreadMessagesExist(Client senderWithId) {
        Client receiver = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Client sender = mainRepository.find(Client.class, senderWithId.getId());

        if (receiver == null || sender == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(messageEJB.isUnreadMessagesExist(receiver, sender)).build();
    }

    @GET
    @Path("/get-unread")
    public Response getUnreadMessages(Client senderWithId) {
        Client receiver = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Client sender = mainRepository.find(Client.class, senderWithId.getId());

        if (receiver == null || sender == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(messageEJB.getUnreadMessages(receiver, sender)).build();
    }

    @POST
    @Path("/send")
    public Response sendMessage(Message message) {
        Client sender = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        if (message.getReceiver() == null || message.getTextMessage() == null || message.getDate() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Client receiver = mainRepository.find(Client.class, message.getReceiver().getId());
        if (sender == null || receiver == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        message.setSender(sender);
        message.setReceiver(receiver);
        mainRepository.create(message);

        return Response.ok(message.toJson()).build();
    }
}
