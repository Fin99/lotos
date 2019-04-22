package com.fin.controller;

import com.fin.dto.GradeBookEntryDto;
import com.fin.ejb.EducatorEJB;
import com.fin.entity.group.GradeBook;
import com.fin.security.Role;
import com.fin.security.Secured;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/educator")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Getter
@Setter
public class EducatorController {

    @Context
    private SecurityContext securityContext;

    @EJB
    private EducatorEJB educatorEJB;

    @POST
    @Path("/get-grade-book")
    @Secured(Role.EDUCATOR)
    public Response getGradeBook(String stringDate) {
        String username = securityContext.getUserPrincipal().getName();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = fmt.parse(stringDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        JsonObject jsonResponse = educatorEJB.getGradeBook(username, date);
        return Response.ok(jsonResponse).build();
    }

    @POST
    @Path("/fill-grade-book")
    @Secured(Role.EDUCATOR)
    public Response addGradeBookEntries(List<GradeBookEntryDto> gradeBookEntryDtoList) {
        String username = securityContext.getUserPrincipal().getName();
        educatorEJB.fillGradeBookList(gradeBookEntryDtoList, username);
        return Response.ok().build();
    }

}
