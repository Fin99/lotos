package com.fin.controller;

import com.fin.entity.Children;
import com.fin.entity.Parent;
import com.fin.entity.employee.*;
import com.fin.entity.group.Group;
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
        if (clientRepository.findByUsername(parent.getClient().getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        parent.getClient().setRole(Role.PARENT);

        mainRepository.create(parent);

        return Response.ok(parent.toJson()).build();
    }

    @POST
    @Path("/children")
    public Response registrationChildren(Children children) {
        if (clientRepository.findByUsername(children.getClient().getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        children.getClient().setRole(Role.CHILDREN);

        mainRepository.create(children);

        return Response.ok(children.toJson()).build();
    }

    @POST
    @Path("/place")
    public Response registrationPlace(Place place) {
        if (place.getId() != 0 && mainRepository.find(Place.class, place.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            mainRepository.create(place);

            return Response.ok(place.toJson()).build();
        }
    }


    @POST
    @Path("/group")
    public Response registrationGroup(Group group) {
        if (group.getId() != 0 && mainRepository.find(Group.class, group.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            mainRepository.create(group);

            return Response.ok(group.toJson()).build();
        }
    }

    @POST
    @Path("/item")
    public Response registrationItem(Item item) {
        if (item.getId() != 0 && mainRepository.find(Item.class, item.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            if (item.getPlace() != null && item.getPlace().getId() != 0 &&
                    mainRepository.find(Place.class, item.getPlace().getId()) != null
            ) {
                item.setPlace(mainRepository.find(Place.class, item.getPlace().getId()));
            }
            mainRepository.create(item);

            return Response.ok(item.toJson()).build();
        }
    }

    @POST
    @Path("/employee")
    public Response registrationEmployee(Employee employee) {
        if (employee.getId() != 0 && mainRepository.find(Employee.class, employee.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            employee.getClient().setRole(parseRole(employee.getTypeEmployee()));

            switch (employee.getTypeEmployee()) {
                case BABYSITTER:
                    employee = new Babysitter(employee);
                    break;
                case CHIEF:
                    employee = new Chief(employee);
                    break;
                case EDUCATOR:
                    employee = new Educator(employee);
                    break;
                case SECURITY:
                    employee = new Security(employee);
                    break;
                case TEACHER:
                    employee = new Teacher(employee);
                    break;
            }

            mainRepository.create(employee);

            return Response.ok(employee.toJson()).build();
        }


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
}
