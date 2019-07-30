package com.fin.controller;

import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.medical.Ill;
import com.fin.entity.medical.MedicalBook;
import com.fin.repository.ChildRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.medical.MedicalBookRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/doctor")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorController {
    @Inject
    ChildRepository childRepository;

    @Inject
    MedicalBookRepository medicalBookRepository;

    @Inject
    MainRepository mainRepository;

    @POST
    @Path("/create-medical-book")
    @Secured(Role.DOCTOR)
    public Response createMedicalBook(List<Object> data) {
        try {
            Child childWithId = (Child) data.get(0);
            MedicalBook medicalBook = (MedicalBook) data.get(1);

            Client childClient = new Client();
            childClient.setId(childWithId.getId());
            Child child = childRepository.findByClient(childClient);

            child.setMedicalBook(medicalBook);
            mainRepository.update(child);

            return Response.ok().build();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/heal")
    @Secured(Role.DOCTOR)
    public Response heal(List<Object> data) {
        try {
            Child childWithId = (Child) data.get(0);
            Ill illWithId = (Ill) data.get(1);

            Client childClient = new Client();
            childClient.setId(childWithId.getId());
            Child child = childRepository.findByClient(childClient);

            child.getMedicalBook().getIllSet().removeIf(ill -> ill.getId() == illWithId.getId());
            mainRepository.update(child);

            return Response.ok().build();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
