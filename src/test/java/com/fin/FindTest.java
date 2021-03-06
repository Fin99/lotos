package com.fin;

import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Educator;
import com.fin.entity.employee.Employee;
import com.fin.entity.group.Group;
import com.fin.entity.place.Place;
import com.fin.security.Role;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.fin.Data.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FindTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.url;
        RestAssured.port = Data.port;
    }

    @Test
    public void testParentsFindAll() {
        Long[] parentsId = createEntity(urlRegistrationParent, getListParents());

        RequestSpecification findRequest = RestAssured.given();
        findRequest.header("Content-Type", "application/json");
        findRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        findRequest.body(new Parent().toJson().toString());

        Response response = findRequest.post(urlFindParent);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertTrue(jsonPath.size() >= 5);

        removeEntity(urlRemoveParent, parentsId);
    }

    @Test
    public void testGroupFindAll() {
        RequestSpecification findRequest = RestAssured.given();
        findRequest.header("Content-Type", "application/json");
        findRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        findRequest.body(new Group().toJson().toString());

        Response response = findRequest.post(urlFindGroup);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 5);
    }

    @Test
    public void testPlaceFindAll() {
        Long[] placesId = createEntity(urlRegistrationPlace, getListPlace());

        RequestSpecification findRequest = RestAssured.given();
        findRequest.header("Content-Type", "application/json");
        findRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        findRequest.body(new Place().toJson().toString());

        Response response = findRequest.post(urlFindPlace);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertTrue(jsonPath.size() >= 5);

        removeEntity(urlRemovePlace, placesId);
    }

    @Test
    public void testParentsByUsername() {
        Long[] parentsId = createEntity(urlRegistrationParent, getListParents());
        Parent parent = new Parent();
        Client client = new Client("Test", null, Role.PARENT);
        parent.setClient(client);

        RequestSpecification findRequest = RestAssured.given();
        findRequest.header("Content-Type", "application/json");
        findRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        findRequest.body(parent.toJson().toString());

        Response response = findRequest.post(urlFindParent);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 5);

        removeEntity(urlRemoveParent, parentsId);
    }

    @Test
    public void testChildrenBySurname() {
        Long[] childrenId = createEntity(urlRegistrationChildren, getListChildren());

        Child child = new Child();
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
    public void testPlaceByName() {
        Long[] placesId = createEntity(urlRegistrationPlace, getListPlace());

        RequestSpecification findRequest = RestAssured.given();
        findRequest.header("Content-Type", "application/json");
        findRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        findRequest.body(new Place("placeTest1").toJson().toString());

        Response response = findRequest.post(urlFindPlace);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(1, jsonPath.size());

        removeEntity(urlRemovePlace, placesId);
    }

    @Test
    public void testEmployeeByName() {
        Long[] employeeId = createEntity(urlRegistrationEmployee, getListEmployee());

        Employee employee = new Educator();
        employee.setTypeEmployee(Employee.TypeEmployee.EDUCATOR);
        employee.setName("Pavel");
        Client client = new Client();
        client.setRole(Role.EDUCATOR);
        employee.setClient(client);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

        removeRequest.body(employee.toJson().toString());

        Response response = removeRequest.post(urlFindEmployee);
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");

        removeEntity(urlRemoveEmployee, employeeId);
        assertEquals(jsonPath.size(), 1);
    }
}
