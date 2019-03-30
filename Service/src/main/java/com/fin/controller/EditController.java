package com.fin.controller;


import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.ParentRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/edit")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EditController {
    @Context
    SecurityContext securityContext;
    @Inject
    ParentRepository parentRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    MainRepository mainRepository;

    @POST
    @Path("/parent")
    @Secured(Role.PARENT)
    public Response editParent(Parent parentData) {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Parent parent = parentRepository.findByClient(client);

        if (parentData.getName() != null) {
            parent.setName(parentData.getName());
        }
        if (parentData.getSurname() != null) {
            parent.setSurname(parentData.getSurname());
        }
        if (parentData.getSex() != null) {
            parent.setSex(parentData.getSex());
        }
        if (parentData.getPhoneNumber() != null) {
            parent.setPhoneNumber(parentData.getPhoneNumber());
        }
        if (parentData.getClient() != null && parentData.getClient().getUsername() != null &&
                clientRepository.findByUsername(parentData.getClient().getUsername()) == null) {
            parent.setClient(parentData.getClient());
        }
        mainRepository.update(parent);

        return Response.ok(parent.toJson()).build();
    }
}
