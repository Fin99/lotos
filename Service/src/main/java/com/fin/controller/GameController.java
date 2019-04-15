package com.fin.controller;

import com.fin.ejb.FightEJB;
import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.repository.ChildRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {
    @Context
    private SecurityContext securityContext;

    @Inject
    private ChildRepository childRepository;

    // TODO add getAllFights()

    @EJB
    private FightEJB fightEJB;

    @POST
    @Secured(Role.EDUCATOR)
    @Path("/start")
    public Response startFight(List<Child> children) {
        Child child1WithId = children.get(0);
        Child child2WithId = children.get(1);

        Client client1WithId = new Client();
        client1WithId.setId(child1WithId.getId());
        Client client2WithId = new Client();
        client2WithId.setId(child2WithId.getId());

        Child radiant = childRepository.findByClient(client1WithId);
        Child dire = childRepository.findByClient(client2WithId);

        if (fightEJB.isFightPossible(radiant, dire) && radiant != null && dire != null) {
            return Response.ok(fightEJB.generateReport(radiant, dire)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/get-last")
    public Response getLastFight() {
        if (fightEJB.getLastFight() != null) {
            return Response.ok(fightEJB.getLastFight().toJson()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
