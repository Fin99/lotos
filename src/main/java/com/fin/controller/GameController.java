package com.fin.controller;

import com.fin.dto.BetDto;
import com.fin.ejb.FightEJB;
import com.fin.entity.Child;
import com.fin.repository.ChildRepository;
import com.fin.repository.MainRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.JsonObject;
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
    @Inject
    private MainRepository mainRepository;

    @EJB
    private FightEJB fightEJB;

    @POST
    @Secured(Role.EDUCATOR)
    @Path("/start")
    public Response startFight(List<Child> children) {
        Child child1WithId = children.get(0);
        Child child2WithId = children.get(1);

        Child radiant = mainRepository.find(Child.class, child1WithId.getId());
        Child dire = mainRepository.find(Child.class, child2WithId.getId());

        if (radiant != null && dire != null && fightEJB.isFightPossible(radiant, dire)) {
            fightEJB.startPreparation(radiant, dire);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Secured(Role.PARENT)
    @Path("/is-bet-possible")
    public Response isBetPossible() {
        JsonObject jsonResponse = fightEJB.getFightersToPlaceBet();
        if (jsonResponse == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return Response.ok(jsonResponse).build();
        }
    }

    @POST
    @Secured(Role.PARENT)
    @Path("/place-bet")
    public Response placeBet(BetDto betDto) {
        if (fightEJB.placeBet(betDto)) {
            return Response.ok().build();
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
