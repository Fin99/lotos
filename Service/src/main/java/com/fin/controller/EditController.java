package com.fin.controller;


import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.group.Group;
import com.fin.entity.medical.MedicalBook;
import com.fin.repository.ChildrenRepository;
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
    ChildrenRepository childrenRepository;
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

    @POST
    @Path("/children")
    @Secured(Role.CHILDREN)
    public Response editChildren(Children childrenData) {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Children children = childrenRepository.findByClient(client);

        if (childrenData.getName() != null) {
            children.setName(childrenData.getName());
        }
        if (childrenData.getSurname() != null) {
            children.setSurname(childrenData.getSurname());
        }
        if (childrenData.getParent1() != null && childrenData.getParent1().getId() != 0) {
            Parent parent = mainRepository.find(Parent.class, children.getParent1().getId());
            if (parent == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            children.setParent1(parent);
        }
        if (childrenData.getParent2() != null && childrenData.getParent2().getId() != 0) {
            Parent parent = mainRepository.find(Parent.class, children.getParent2().getId());
            if (parent == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            children.setParent2(parent);
        }
        if (childrenData.getGroup() != null && childrenData.getGroup().getId() != 0) {
            Group group = mainRepository.find(Group.class, children.getGroup().getId());
            if (group == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            children.setGroup(group);
        }
        if (childrenData.getMedicalBook() != null && childrenData.getMedicalBook().getId() != 0) {
            MedicalBook medicalBook = mainRepository.find(MedicalBook.class, children.getMedicalBook().getId());
            if (medicalBook == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            children.setMedicalBook(medicalBook);
        }

        mainRepository.update(children);

        return Response.ok(children.toJson()).build();
    }
}
