package com.fin.controller;

import com.fin.entity.Child;
import com.fin.entity.Parent;
import com.fin.entity.employee.*;
import com.fin.entity.group.Group;
import com.fin.entity.group.TypeGroup;
import com.fin.entity.medical.MedicalBook;
import com.fin.entity.money.Wallet;
import com.fin.entity.place.Item;
import com.fin.entity.place.Place;
import com.fin.repository.ClientRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.place.ItemRepository;
import com.fin.repository.place.PlaceRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registration")
@Secured({Role.CHIEF})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationController {
    @Inject
    ClientRepository clientRepository;
    @Inject
    MainRepository mainRepository;
    @Inject
    PlaceRepository placeRepository;
    @Inject
    ItemRepository itemRepository;

    @POST
    @Path("/parent")
    public Response registrationParent(Parent parent) {
        if (clientRepository.findByUsername(parent.getClient().getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        Wallet wallet = new Wallet();
        mainRepository.create(wallet);
        parent.setWallet(wallet);

        parent.getClient().setRole(Role.PARENT);

        mainRepository.create(parent);

        return Response.ok(parent.toJson()).build();
    }

    private Parent validateParent(Parent parent) {
        if (parent != null) {
            Parent received = mainRepository.find(Parent.class, parent.getId());
            if (received == null) {
                throw new NotFoundException("Parent not found in db");
            }
            return received;
        } else {
            return null;
        }
    }

    @POST
    @Path("/child")
    public Response registrationChildren(Child child) {
        System.out.println(child.toJson().toString());
        if (clientRepository.findByUsername(child.getClient().getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        try {
            child.setParent1(validateParent(child.getParent1()));
            child.setParent2(validateParent(child.getParent2()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        MedicalBook medicalBook = new MedicalBook();
        mainRepository.create(medicalBook);
        child.setMedicalBook(medicalBook);

        child.getClient().setRole(Role.CHILD);

        mainRepository.create(child);

        return Response.ok(child.toJson()).build();
    }

    @POST
    @Path("/place")
    public Response registrationPlace(Place place) {
        if (place.getId() != 0 && mainRepository.find(Place.class, place.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            mainRepository.create(place);

            return Response.ok(place.toJson()).build();
        }
    }


    @POST
    @Path("/group")
    public Response registrationGroup(Group group) {
        System.out.println(group.toJson());
        if (mainRepository.find(Group.class, group.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        if (group.getTypeGroup() != null) {
            TypeGroup typeGroup = mainRepository.find(TypeGroup.class, group.getTypeGroup().getId());
            if (typeGroup != null) {
                group.setTypeGroup(typeGroup);
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (group.getTeacher() != null) {
            Teacher teacher = mainRepository.find(Teacher.class, group.getTeacher().getId());
            if (teacher != null) {
                group.setTeacher(teacher);
            }
        }
        if (group.getBabysitter() != null) {
            Babysitter babysitter = mainRepository.find(Babysitter.class, group.getBabysitter().getId());
            if (babysitter != null) {
                group.setBabysitter(babysitter);
            }
        }
        if (group.getEducator1() != null) {
            Educator educator = mainRepository.find(Educator.class, group.getEducator1().getId());
            if (educator != null) {
                group.setEducator1(educator);
            }
        }
        if (group.getEducator2() != null) {
            Educator educator = mainRepository.find(Educator.class, group.getEducator2().getId());
            if (educator != null) {
                group.setEducator2(educator);
            }
        }

        mainRepository.create(group);

        return Response.ok(group.toJson()).build();
    }

    @POST
    @Path("/item")
    public Response registrationItem(Item item) {
        if (mainRepository.find(Item.class, item.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        if (item.getPlace() != null) {
            Place place = mainRepository.find(Place.class, item.getPlace().getId());
            if (place != null) {
                item.setPlace(place);
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        mainRepository.create(item);

        return Response.ok(item.toJson()).build();

    }

    @POST
    @Path("/employee")
    public Response registrationEmployee(Employee employee) {
        if (employee.getId() != 0 && mainRepository.find(Employee.class, employee.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            employee.getClient().setRole(parseRole(employee.getTypeEmployee()));

            switch (employee.getTypeEmployee()) {
                case BABYSITTER:
                    employee = new Babysitter(employee);
                    break;
                case CHIEF:
                    employee = new Chief(employee);
                    break;
                case EDUCATOR:
                    employee = new Educator(employee);
                    break;
                case SECURITY:
                    employee = new Security(employee);
                    break;
                case TEACHER:
                    employee = new Teacher(employee);
                    break;
                case COOKER:
                    employee = new Cooker(employee);
                    break;
                case DOCTOR:
                    employee = new Doctor(employee);
                    break;
            }

            mainRepository.create(employee);

            return Response.ok(employee.toJson()).build();
        }


    }

    private Role parseRole(Employee.TypeEmployee typeEmployee) {
        switch (typeEmployee) {
            case BABYSITTER:
                return Role.BABYSITTER;
            case CHIEF:
                return Role.CHIEF;
            case EDUCATOR:
                return Role.EDUCATOR;
            case SECURITY:
                return Role.SECURITY;
            case TEACHER:
                return Role.TEACHER;
            case COOKER:
                return Role.COOKER;
            case DOCTOR:
                return Role.DOCTOR;
            default:
                return null;
        }
    }
}

