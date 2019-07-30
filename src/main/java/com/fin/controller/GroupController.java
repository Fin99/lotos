package com.fin.controller;

import com.fin.ejb.GroupEJB;
import com.fin.entity.group.Group;
import com.fin.entity.group.TypeGroup;
import com.fin.security.Role;
import com.fin.security.Secured;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/group")
@Secured(Role.CHIEF)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Getter
@Setter
public class GroupController {

    @Context
    private SecurityContext securityContext;

    @EJB
    private GroupEJB groupEJB;

    @POST
    @Path("/get-all")
    public Response getGroups() {
        JsonArray responseJson = groupEJB.getGroups();
        if (responseJson == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(responseJson).build();
    }

    @POST
    @Path("/create")
    public Response createGroup(Group group) {
        if (groupEJB.addGroup(group)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/update")
    public Response updateGroup(Group group) {
        if (groupEJB.updateGroup(group)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/remove")
    public Response removeGroup(long id) {
        if (groupEJB.removeGroup(id)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
