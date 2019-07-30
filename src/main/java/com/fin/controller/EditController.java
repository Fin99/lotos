package com.fin.controller;


import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.entity.group.Group;
import com.fin.entity.medical.MedicalBook;
import com.fin.repository.ChildRepository;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.employee.EmployeeRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/edit")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EditController {
    @Context
    SecurityContext securityContext;

    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    ParentRepository parentRepository;
    @Inject
    ChildRepository childRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    MainRepository mainRepository;

    @POST
    @Path("/parent")
    @Secured(Role.PARENT)
    public Response editParent(Parent parentData) {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Parent parent = parentRepository.findByClient(client);

        if (parentData.getName() != null) {
            parent.setName(parentData.getName());
        }
        if (parentData.getSurname() != null) {
            parent.setSurname(parentData.getSurname());
        }
        if (parentData.getSex() != null) {
            parent.setSex(parentData.getSex());
        }
        if (parentData.getPhoneNumber() != null) {
            parent.setPhoneNumber(parentData.getPhoneNumber());
        }
        if (parentData.getClient() != null && parentData.getClient().getUsername() != null &&
                clientRepository.findByUsername(parentData.getClient().getUsername()) == null) {
            parent.getClient().setUsername(parentData.getClient().getUsername());
        }
        mainRepository.update(parent);

        return Response.ok(parent.toJson()).build();
    }

    @POST
    @Path("/child")
    @Secured(Role.CHILD)
    public Response editChildren(Child childData) {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Child child = childRepository.findByClient(client);

        if (childData.getName() != null) {
            child.setName(childData.getName());
        }
        if (childData.getSurname() != null) {
            child.setSurname(childData.getSurname());
        }
        if (childData.getParent1() != null && childData.getParent1().getId() != 0) {
            Parent parent = mainRepository.find(Parent.class, child.getParent1().getId());
            if (parent == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            child.setParent1(parent);
        }
        if (childData.getParent2() != null && childData.getParent2().getId() != 0) {
            Parent parent = mainRepository.find(Parent.class, child.getParent2().getId());
            if (parent == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            child.setParent2(parent);
        }
        if (childData.getGroup() != null && childData.getGroup().getId() != 0) {
            Group group = mainRepository.find(Group.class, child.getGroup().getId());
            if (group == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            child.setGroup(group);
        }
        if (childData.getMedicalBook() != null && childData.getMedicalBook().getId() != 0) {
            MedicalBook medicalBook = mainRepository.find(MedicalBook.class, child.getMedicalBook().getId());
            if (medicalBook == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            child.setMedicalBook(medicalBook);
        }
        if (childData.getClient() != null && childData.getClient().getUsername() != null &&
                clientRepository.findByUsername(childData.getClient().getUsername()) == null) {
            child.getClient().setUsername(childData.getClient().getUsername());
        }
        mainRepository.update(child);

        return Response.ok(child.toJson()).build();
    }

    @POST
    @Path("/employee")
    @Secured(value = {Role.EDUCATOR, Role.CHIEF, Role.TEACHER, Role.BABYSITTER,
            Role.SECURITY, Role.COOKER, Role.DOCTOR})
    public Response editEmployee(Employee employeeData) {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Employee employee = employeeRepository.findByClient(client);

        if (employeeData.getName() != null) {
            employee.setName(employeeData.getName());
        }
        if (employeeData.getSurname() != null) {
            employee.setSurname(employeeData.getSurname());
        }
        if (employeeData.getInn() != null) {
            employee.setInn(employeeData.getInn());
        }
        if (employeeData.getPassport() != null) {
            employee.setPassport(employeeData.getPassport());
        }
        if (employeeData.getPhoneNumber() != null) {
            employee.setPhoneNumber(employeeData.getPhoneNumber());
        }
        if (employeeData.getClient() != null && employeeData.getClient().getUsername() != null &&
                clientRepository.findByUsername(employeeData.getClient().getUsername()) == null) {
            employee.getClient().setUsername(employeeData.getClient().getUsername());
        }
        mainRepository.update(employee);

        return Response.ok(employee.toJson()).build();
    }
}
