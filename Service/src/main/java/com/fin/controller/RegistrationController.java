package com.fin.controller;

import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.*;
import com.fin.entity.place.Item;
import com.fin.entity.place.Place;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.place.ItemRepository;
import com.fin.repository.place.PlaceRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registration")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Secured({Role.CHIEF})
public class RegistrationController {
    @Inject
    ClientRepository clientRepository;
    @Inject
    MainRepository mainRepository;
    @Inject
    PlaceRepository placeRepository;
    @Inject
    ItemRepository itemRepository;

    @POST
    @Path("/parent")
    public Response registrationParent(Parent parent) {
        if (!createClient(parent.getClient(), Role.PARENT)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        mainRepository.create(parent);

        return Response.ok().build();
    }

    @POST
    @Path("/children")
    public Response registrationChildren(Children children) {
        if (!createClient(children.getClient(), Role.CHILDREN)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        mainRepository.create(children);

        return Response.ok().build();
    }

    @POST
    @Path("/place")
    public Response registrationPlace(Place place) {
        if (placeRepository.findPlace(place.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        mainRepository.create(place);

        return Response.ok(place.toJson()).build();
    }

    @POST
    @Path("/item")
    public Response registrationItem(Item item) {
        if (itemRepository.findItem(item.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        if(item.getPlace() != null){
            item.setPlace(placeRepository.findPlace(item.getPlace().getId()));
        }

        mainRepository.create(item);

        return Response.ok(item.toJson()).build();
    }

    @POST
    @Path("/employee")
    public Response registrationEmployee(Employee employee) {
        if (!createClient(employee.getClient(), parseRole(employee.getTypeEmployee()))) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        switch (employee.getTypeEmployee()) {
            case BABYSITTER:
                mainRepository.create(new Babysitter(employee));
                break;
            case CHIEF:
                mainRepository.create(new Chief(employee));
                break;
            case EDUCATOR:
                mainRepository.create(new Educator(employee));
                break;
            case SECURITY:
                mainRepository.create(new Security(employee));
                break;
            case TEACHER:
                mainRepository.create(new Teacher(employee));
                break;
        }

        return Response.ok().build();
    }

    private Role parseRole(Employee.TypeEmployee typeEmployee) {
        switch (typeEmployee) {
            case BABYSITTER:
                return Role.BABYSITTER;
            case CHIEF:
                return Role.CHIEF;
            case EDUCATOR:
                return Role.EDUCATOR;
            case SECURITY:
                return Role.SECURITY;
            case TEACHER:
                return Role.TEACHER;
            default:
                return null;
        }
    }

    private boolean createClient(Client client, Role role) {
        if (role == null) {
            return false;
        }

        if (clientRepository.findByUsername(client.getUsername()) != null) {
            return false;
        }

        client.setRole(role);

        mainRepository.create(client);
        return true;
    }
}
