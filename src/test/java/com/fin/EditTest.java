package com.fin;

import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.entity.employee.Teacher;
import com.fin.security.Role;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

import static com.fin.Data.*;
import static org.junit.Assert.assertEquals;

public class EditTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = url;
        RestAssured.port = port;
    }

    @Test
    public void testParentRename() {
        Long[] idParent = createEntity(urlRegistrationParent, parent);

        RequestSpecification editRequest = RestAssured.given();
        editRequest.header("Content-Type", "application/json");
        editRequest.header("Authorization", "Bearer " +
            getToken(parent.getClient().getCredentials()));

        JsonObject editParent = Json.createObjectBuilder(parent.toJson())
            .remove("name")
            .add("name", "parentTestEdit")
            .add("id", idParent[0]).build();

        editRequest.body(editParent.toString());

        int editStatus = editRequest.post(urlEditParent).getStatusCode();

        assertEquals(editStatus, 200);

        // find this parent
        Parent findParent = new Parent();
        findParent.setName("parentTestEdit");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));
        removeRequest.body(findParent.toJson().toString());
        Response response = removeRequest.post(urlFindParent);
        List<Object> jsonPath = response.body().jsonPath().getList("");

        //check change
        assertEquals(jsonPath.size(), 1);

        removeEntity(urlRemoveParent, idParent);
    }

    @Test
    public void testNothingEditParent() {
        Long[] idParent = createEntity(urlRegistrationParent, parent);

        RequestSpecification editRequest = RestAssured.given();
        editRequest.header("Content-Type", "application/json");
        editRequest.header("Authorization", "Bearer " +
            getToken(parent.getClient().getCredentials()));

        JsonObject editParent = Json.createObjectBuilder(parent.toJson())
            .add("id", idParent[0]).build();

        editRequest.body(editParent.toString());

        int editStatus = editRequest.post(urlEditParent).getStatusCode();

        assertEquals(editStatus, 200);

        removeEntity(urlRemoveParent, idParent);
    }

    @Test
    public void testChildrenRename() {
        Long[] idChildren = createEntity(urlRegistrationChildren, child);

        RequestSpecification editRequest = RestAssured.given();
        editRequest.header("Content-Type", "application/json");
        editRequest.header("Authorization", "Bearer " +
            getToken(child.getClient().getCredentials()));

        JsonObject editParent = Json.createObjectBuilder(child.toJson())
            .remove("name")
            .add("name", "childrenTestEdit")
            .add("id", idChildren[0]).build();

        editRequest.body(editParent.toString());

        int editStatus = editRequest.post(urlEditChildren).getStatusCode();

        assertEquals(editStatus, 200);

        // find this child
        Child findChild = new Child();
        findChild.setName("childrenTestEdit");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));
        removeRequest.body(findChild.toJson().toString());
        Response response = removeRequest.post(urlFindChildren);
        List<Object> jsonPath = response.body().jsonPath().getList("");

        //check change
        assertEquals(jsonPath.size(), 1);

        removeEntity(urlRemoveChildren, idChildren);
    }

    @Test
    public void testEmployeeRename() {
        Long[] idTeacher = createEntity(urlRegistrationEmployee, teacher);

        RequestSpecification editRequest = RestAssured.given();
        editRequest.header("Content-Type", "application/json");
        editRequest.header("Authorization", "Bearer " +
            getToken(teacher.getClient().getCredentials()));

        JsonObject editTeacher = Json.createObjectBuilder(teacher.toJson())
            .remove("name")
            .add("name", "teacherTestEdit")
            .add("id", idTeacher[0]).build();

        editRequest.body(editTeacher.toString());

        int editStatus = editRequest.post(urlEditEmployee).getStatusCode();

        assertEquals(editStatus, 200);

        // find this child
        Teacher findTeacher = new Teacher();
        findTeacher.setTypeEmployee(Employee.TypeEmployee.TEACHER);
        findTeacher.setName("teacherTestEdit");

        Client teacherClient = new Client();
        teacherClient.setRole(Role.TEACHER);
        findTeacher.setClient(teacherClient);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));
        System.out.println(findTeacher.toJson().toString());
        removeRequest.body(findTeacher.toJson().toString());
        Response response = removeRequest.post(urlFindEmployee);
        System.out.println(response.body().prettyPrint());
        List<Object> jsonPath = response.body().jsonPath().getList("");

        removeEntity(urlRemoveEmployee, idTeacher);
        //check change
        assertEquals(jsonPath.size(), 1);

    }
}
