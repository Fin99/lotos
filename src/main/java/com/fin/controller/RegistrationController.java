package com.fin.controller;

import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.repository.ChildrenRepository;
import com.fin.repository.ClientRepository;
import com.fin.repository.ParentRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

@Path("/registration")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RegistrationController {
    @Inject
    ClientRepository clientRepository;
    @Inject
    ParentRepository parentRepository;
    @Inject
    ChildrenRepository childrenRepository;

    @POST
    @Secured({Role.CHIEF})
    @Path("/parent")
    public Response registrationParent(Parent parent) {
        if (createClient(parent.getClient(), Role.PARENT)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        parentRepository.create(parent);

        return Response.ok().build();
    }

    @POST
    @Secured({Role.CHIEF})
    @Path("/children")
    public Response registrationChildren(Children children) {
        if (createClient(children.getClient(), Role.EDUCATOR)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        childrenRepository.create(children);

        return Response.ok().build();
    }

    private boolean createClient(Client client, Role role) {
        if (clientRepository.findByUsername(client.getUsername()) != null) {
            return false;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        client.setRoles(roles);

        clientRepository.create(client);
        return true;
    }
}
