package com.fin.controller;

import com.fin.ejb.GameEJB;
import com.fin.entity.Children;
import com.fin.entity.medical.DiseaseStrength;
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

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {
    @Context
    private SecurityContext securityContext;

    // TODO add getAllFights()

    @EJB
    private GameEJB gameEJB;

    @POST
    @Secured(Role.TEACHER)
    @Path("/start")
    public Response startFight(Children radiant, Children dire) {
        if (gameEJB.isFightPossible(radiant, dire)) {
            gameEJB.startFight(radiant, dire);
            return Response.ok().build(); // FIXME Send success.
        } else {
            return Response.ok().build(); // FIXME Send to recipient the reason.
        }

    }
}
