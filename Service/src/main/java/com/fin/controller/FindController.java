package com.fin.controller;


import com.fin.entity.Jsonable;
import com.fin.entity.Parent;
import com.fin.repository.ParentRepository;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/find")
@Secured
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class FindController {
    @Inject
    ParentRepository parentRepository;

    @POST
    @Path("/parent")
    public Response findParent(Parent parentData) {
        if (parentData.getClient() != null && parentData.getClient().getUsername() != null ||
                parentData.getName() != null || parentData.getSurname() != null ||
                parentData.getSex() != null || parentData.getPhoneNumber() != null
        ) {
            List<Parent> parents = parentRepository.findParents(parentData);
            return Response.ok(wrapList(parents)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private <T extends Jsonable> JsonArray wrapList(List<T> entities) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (T entity : entities) {
            jsonArrayBuilder.add(entity.toJson());
        }

        return jsonArrayBuilder.build();
    }
}
