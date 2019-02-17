package com.fin.controller;

import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.repository.ChildrenRepository;
import com.fin.repository.ClientRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.employee.EmployeeRepository;
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
    ParentRepository parentRepository;
    @Inject
    ChildrenRepository childrenRepository;
    @Inject
    EmployeeRepository employeeRepository;

    @POST
    @Path("/parent")
    public Response registrationParent(Parent parent) {
        if (!createClient(parent.getClient(), Role.PARENT)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        parentRepository.create(parent);

        return Response.ok().build();
    }

    @POST
    @Path("/children")
    public Response registrationChildren(Children children) {
        if (createClient(children.getClient(), Role.EDUCATOR)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        childrenRepository.create(children);

        return Response.ok().build();
    }

    @POST
    @Path("/employee")
    public Response registrationEmployee(Employee employee) {
        if (createClient(employee.getClient(), parseRole(employee.getTypeEmployee()))) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        employeeRepository.create(employee);

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

        clientRepository.create(client);
        return true;
    }
}
