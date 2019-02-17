package com.fin.controller;


import com.fin.entity.Client;
import com.fin.entity.employee.Employee;
import com.fin.repository.ClientRepository;
import com.fin.repository.employee.EmployeeRepository;
import com.fin.security.Credentials;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/chief")
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
    public Response authenticateClient(Credentials credentials) {
        String username = securityContext.getUserPrincipal().getName();

        Client client = clientRepository.findByUsername(username);
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Employee employee = employeeRepository.findByClient(client);
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(employee.toJson()).build();
    }
}
