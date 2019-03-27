package com.fin.controller;

import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.entity.group.Group;
import com.fin.entity.place.Item;
import com.fin.entity.place.Place;
import com.fin.repository.ChildrenRepository;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.employee.EmployeeRepository;
import com.fin.repository.place.ItemRepository;
import com.fin.repository.place.PlaceRepository;
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

@Path("/remove")
@Secured(Role.CHIEF)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RemoveController {
    @Context
    SecurityContext securityContext;

    @Inject
    MainRepository mainRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    ChildrenRepository childrenRepository;
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    ParentRepository parentRepository;
    @Inject
    PlaceRepository placeRepository;
    @Inject
    ItemRepository itemRepository;

    @POST
    @Path("/children")
    public Response removeChildren(Client username) {
        Client removeClient = clientRepository.findByUsername(username.getUsername());
        if (removeClient == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Children removeChildren = childrenRepository.findByClient(removeClient);
        if (removeChildren == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.remove(Children.class, removeChildren.getId());

        return Response.ok().build();
    }

    @POST
    @Path("/parent")
    public Response removeParent(Client username) {
        if (isChiefWantDeleteSelf(username.getUsername())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Client removeClient = clientRepository.findByUsername(username.getUsername());
        if (removeClient == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Parent removeParent = parentRepository.findByClient(removeClient);
        if (removeParent == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.remove(Parent.class, removeParent.getId());

        return Response.ok().build();
    }

    @POST
    @Path("/employee")
    public Response removeEmployee(Client username) {
        if (isChiefWantDeleteSelf(username.getUsername())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Client removeClient = clientRepository.findByUsername(username.getUsername());
        if (removeClient == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Employee removeEmployee = employeeRepository.findByClient(removeClient);
        if (removeEmployee == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.remove(Employee.class, removeEmployee.getId());

        return Response.ok().build();
    }

    @POST
    @Path("/place")
    public Response removePlace(Place placeId) {
        if (deleteEntityIfNotNull(Place.class, placeId.getId())) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/item")
    public Response removeItem(Item itemId) {
        if (deleteEntityIfNotNull(Item.class, itemId.getId())) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/group")
    public Response removeGroup(Group groupId) {
        if (deleteEntityIfNotNull(Group.class, groupId.getId())) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private <T> boolean deleteEntityIfNotNull(Class<T> entityId, long id) {
        if (mainRepository.find(entityId, id) == null) {
            return false;
        } else {
            mainRepository.remove(entityId, id);
            return true;
        }
    }

    private boolean isChiefWantDeleteSelf(String entityUsername) {
        String chiefUsername = securityContext.getUserPrincipal().getName();
        return chiefUsername.equals(entityUsername);
    }
}
