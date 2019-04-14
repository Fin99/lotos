package com.fin.controller;

import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.entity.group.Group;
import com.fin.entity.place.Item;
import com.fin.entity.place.Place;
import com.fin.repository.ChildRepository;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RemoveController {
    @Context
    SecurityContext securityContext;

    @Inject
    MainRepository mainRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    ChildRepository childRepository;
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    ParentRepository parentRepository;
    @Inject
    PlaceRepository placeRepository;
    @Inject
    ItemRepository itemRepository;

    @POST
    @Path("/child")
    public Response removeChildren(Child childId) {
        Child child = mainRepository.find(Child.class, childId.getId());
        if (child == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.remove(Child.class, childId.getId());

        return Response.ok().build();
    }

    @POST
    @Path("/parent")
    public Response removeParent(Parent parentId) {
        Parent parent = mainRepository.find(Parent.class, parentId.getId());
        if (parent == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Client client = mainRepository.find(Client.class, parent.getClient().getId());
        if (isChiefWantDeleteSelf(client.getUsername())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.remove(Parent.class, parentId.getId());

        return Response.ok().build();
    }

    @POST
    @Path("/employee")
    public Response removeEmployee(Employee employeeId) {
        Employee employee = mainRepository.find(Employee.class, employeeId.getId());
        if (employee == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Client client = mainRepository.find(Client.class, employee.getClient().getId());
        if (isChiefWantDeleteSelf(client.getUsername())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.remove(Employee.class, employee.getId());

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
