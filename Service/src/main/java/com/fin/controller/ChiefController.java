package com.fin.controller;


import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Chief;
import com.fin.entity.employee.Employee;
import com.fin.repository.ChildrenRepository;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.employee.EmployeeRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/chief")
@Secured(Role.CHIEF)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ChiefController {
    @Context
    SecurityContext securityContext;

    @Inject
    ClientRepository clientRepository;
    @Inject
    EmployeeRepository employeeRepository;

    @GET
    @Path("/info")
    public Response infoChief() {
        String username = securityContext.getUserPrincipal().getName();

        Client client = clientRepository.findByUsername(username);
        if (client == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Employee employee = employeeRepository.findByClient(client);
        if (employee == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Chief chief = employeeRepository.findChief(employee);
        if (chief == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(chief.toJson()).build();
    }


}