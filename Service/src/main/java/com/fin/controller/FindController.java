package com.fin.controller;


import com.fin.entity.Child;
import com.fin.entity.Jsonable;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.entity.group.Group;
import com.fin.repository.ChildRepository;
import com.fin.repository.MainRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.employee.EmployeeRepository;
import com.fin.repository.group.GroupRepository;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/find")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FindController {
    @Inject
    MainRepository mainRepository;
    @Inject
    ParentRepository parentRepository;
    @Inject
    ChildRepository childRepository;
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    GroupRepository groupRepository;

    @POST
    @Path("/parent")
    public Response findParent(Parent parentData) {
        if (parentData.getClient() != null && parentData.getClient().getUsername() != null ||
                parentData.getName() != null || parentData.getSurname() != null ||
                parentData.getSex() != null || parentData.getPhoneNumber() != null
        ) {
            List<Parent> parents = parentRepository.findParents(parentData);
            return Response.ok(Jsonable.wrapList(parents)).build();
        } else {
            return Response.ok(Jsonable.wrapList(mainRepository.findAll(Parent.class))).build();
        }
    }

    @POST
    @Path("/group")
    public Response findGroup(Group groupData) {
        if (groupData.getName() != null) {
            List<Group> groups = groupRepository.findGroup(groupData);
            return Response.ok(Jsonable.wrapList(groups)).build();
        } else {
            return Response.ok(Jsonable.wrapList(mainRepository.findAll(Group.class))).build();
        }
    }


    @POST
    @Path("/child")
    public Response findChildren(Child childData) {
        if (childData.getClient() != null && childData.getClient().getUsername() != null ||
                childData.getName() != null || childData.getSurname() != null ||
                childData.getMedicalBook() != null && childData.getMedicalBook().getSex() != null
        ) {
            List<Child> children = childRepository.findChildren(childData);
            return Response.ok(Jsonable.wrapList(children)).build();
        } else {
            return Response.ok(Jsonable.wrapList(mainRepository.findAll(Child.class))).build();
        }
    }

    @POST
    @Path("/employee")
    public Response findEmployee(Employee employeeData) {
        if (employeeData.getTypeEmployee() != null &&
                (employeeData.getClient() != null && employeeData.getClient().getUsername() != null ||
                        employeeData.getName() != null || employeeData.getSurname() != null ||
                        employeeData.getPassport() != null || employeeData.getPhone() != null ||
                        employeeData.getInn() != null)
        ) {
            List<Employee> employee = employeeRepository.findEmployee(employeeData);
            return Response.ok(Jsonable.wrapList(employee)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
