package com.fin.controller;

import com.fin.entity.Child;
import com.fin.ejb.FightEJB;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    // TODO add getAllFights()

    @EJB
    private FightEJB fightEJB;

    @POST
    @Secured(Role.TEACHER)
    @Path("/start")
    public Response startFight(List<Child> children) {
        Child radiant = children.get(0);
        Child dire = children.get(1);
        if (fightEJB.isFightPossible(radiant, dire)) {
            fightEJB.generateReport(radiant, dire);
            return Response.ok().build(); // FIXME Send success.
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build(); // FIXME Send to recipient the reason.
        }
    }
}
