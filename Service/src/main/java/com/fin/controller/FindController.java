package com.fin.controller;


import com.fin.entity.Children;
import com.fin.entity.Jsonable;
import com.fin.entity.Parent;
import com.fin.repository.ChildrenRepository;
import com.fin.repository.ParentRepository;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Inject
    ChildrenRepository childrenRepository;

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

    @POST
    @Path("/children")
    public Response findChildren(Children childrenData) {
        if (childrenData.getClient() != null && childrenData.getClient().getUsername() != null ||
                childrenData.getName() != null || childrenData.getSurname() != null ||
                childrenData.getMedicalBook() != null && childrenData.getMedicalBook().getSex() != null
        ) {
            List<Children> children = childrenRepository.findChildren(childrenData);
            return Response.ok(wrapList(children)).build();
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
