package com.fin.controller;

import com.fin.ejb.TeacherEJB;
import com.fin.security.Role;
import com.fin.security.Secured;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/teacher")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Getter
@Setter
public class TeacherController {

    @Context
    private SecurityContext securityContext;

    @EJB
    private TeacherEJB teacherEJB;

    @POST
    @Path("/get-diaries")
    @Secured(Role.TEACHER)
    public Response getDiaries(String stringDate) {
        String teacherUsername = securityContext.getUserPrincipal().getName();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = fmt.parse(stringDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(teacherEJB.getDiaries(teacherUsername, date)).build();
    }
}
