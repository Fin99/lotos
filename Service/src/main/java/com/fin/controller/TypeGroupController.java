package com.fin.controller;

import com.fin.ejb.TypeGroupEJB;
import com.fin.entity.group.TypeGroup;
import com.fin.security.Role;
import com.fin.security.Secured;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/type-group")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Getter
@Setter
public class TypeGroupController {

    @Context
    private SecurityContext securityContext;

    @EJB
    private TypeGroupEJB typeGroupEJB;

    @POST
    @Path("/get-all")
    @Secured(Role.CHIEF)
    public Response getGroupTypes() {
        JsonObject responseJson = typeGroupEJB.getGroupTypes();
        if (responseJson == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(responseJson).build();
    }

    @POST
    @Path("/create")
    @Secured(Role.CHIEF)
    public Response createGroupType(TypeGroup typeGroup) {
        if (typeGroupEJB.addGroupType(typeGroup)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
