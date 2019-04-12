package com.fin;

import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Educator;
import com.fin.entity.employee.Employee;
import com.fin.security.Role;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.fin.Data.*;
import static org.junit.Assert.assertEquals;

public class FindTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.url;
        RestAssured.port = Data.port;
    }

    @Test
    public void testParentsByUsername() {
        Long[] parentsId = createEntity(urlRegistrationParent, getListParents());
        Parent parent = new Parent();
        Client client = new Client("Test", null, Role.PARENT);
        parent.setClient(client);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        removeRequest.body(parent.toJson().toString());

        Response response = removeRequest.post(urlFindParent);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 5);

        removeEntity(urlRemoveParent, parentsId);
    }

    @Test
    public void testChildrenBySurname() {
        Long[] childrenId = createEntity(urlRegistrationChildren, getListChildren());

        Children child = new Children();
        child.setSurname("Isa");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        removeRequest.body(child.toJson().toString());

        Response response = removeRequest.post(urlFindChildren);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 3);

        removeEntity(urlRemoveChildren, childrenId);
    }

    @Test
    public void testEmployeeByName() {
        Long[] employeeId = createEntity(urlRegistrationEmployee, getListEmployee());

        Employee employee = new Educator();
        employee.setTypeEmployee(Employee.TypeEmployee.EDUCATOR);
        employee.setName("Pavel");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        removeRequest.body(employee.toJson().toString());

        Response response = removeRequest.post(urlFindEmployee);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 1);

        removeEntity(urlRemoveEmployee, employeeId);
    }
}
