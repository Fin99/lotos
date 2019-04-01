package com.fin;

import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.Parent;
import com.fin.entity.employee.Educator;
import com.fin.entity.employee.Employee;
import com.fin.entity.employee.Teacher;
import com.fin.entity.group.Group;
import com.fin.entity.group.TypeGroup;
import com.fin.entity.medical.MedicalBook;
import com.fin.entity.place.Item;
import com.fin.entity.place.Place;
import com.fin.security.Credentials;
import com.fin.security.Role;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.json.Json;
import java.util.Date;

import static org.junit.Assert.assertEquals;

class Data {
    static String url = "http://localhost";
    static int port = 8080;

    static String urlRegistrationParent = "/registration/parent";
    static String urlRegistrationChildren = "/registration/children";
    static String urlRegistrationEmployee = "/registration/employee";

    static String urlRemoveParent = "/remove/parent";
    static String urlRemoveChildren = "/remove/children";
    static String urlRemoveEmployee = "/remove/employee";

    static String urlFindParent = "/find/parent";
    static String urlFindChildren = "/find/children";
    static String urlFindEmployee = "/find/employee";

    static String urlEditParent = "/edit/parent";
    static String urlEditChildren = "/edit/children";
    static String urlEditEmployee = "/edit/employee";

    static String urlMessageSend = "/message/send";
    static String urlMessageGet = "/message/get";

    static Credentials chiefCredentials = new Credentials("chief1@gmail.com", "chief");
    static Parent parent = new Parent();
    static Children children = new Children();
    static Teacher teacher = new Teacher();
    static Place place = new Place();
    static Item item = new Item();
    static Group group = new Group();

    static {
        parent.setName("Александр");
        parent.setSurname("Исаев");
        parent.setPhoneNumber("88005553535");
        parent.setSex('м');
        parent.setClient(new Client("parentTest", "parentTest", Role.PARENT));

        children.setName("Александр");
        children.setSurname("Исаев");
        children.setClient(new Client("childrenTest", "childrenTest", Role.CHILDREN));

        teacher.setPhone("88005553535");
        teacher.setPassport("1212453244");
        teacher.setInn("88005553535");
        teacher.setName("Александр");
        teacher.setSurname("Исаев");
        teacher.setSalary(15000);
        teacher.setTypeEmployee(Employee.TypeEmployee.TEACHER);
        teacher.setClient(new Client("teacherTest", "teacherTest", Role.TEACHER));

        place.setName("374TEST");

        item.setName("Pen");
        item.setNote("Red pen");
        item.setPrice(20.3);
        item.setShelfLife(new Date());

        TypeGroup typeGroup = new TypeGroup();
        typeGroup.setId(1);

        group.setName("RomashkaTest");
        group.setTypeGroup(typeGroup);
    }

    static void removeEntity(String url, Long... idList) {
        for (Long id : idList) {
            RequestSpecification removeRequest = RestAssured.given();
            removeRequest.header("Content-Type", "application/json");
            removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

            removeRequest.body(Json.createObjectBuilder()
                    .add("id", id).build().toString());

            assertEquals(removeRequest.post(url).statusCode(), 200);
        }
    }

    static Long[] createEntity(String url, Jsonable... entities) {
        Long[] idList = new Long[entities.length];

        int i = 0;
        for (Jsonable entity : entities) {
            RequestSpecification registrationRequest = RestAssured.given();
            registrationRequest.header("Content-Type", "application/json");
            registrationRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));

            registrationRequest.body(entity.toJson().toString());

            Response response = registrationRequest.post(url);
            assertEquals(response.statusCode(), 200);

            long id = response.getBody().jsonPath().getLong("id");
            idList[i++] = id;
        }
        return idList;
    }

    static Employee[] getListEmployee() {
        Employee[] employee = new Employee[5];

        Client client1 = new Client("employeeTest1", "pass", Role.EDUCATOR);
        Employee employee1 = new Educator("Alexandr", "Isaev", "+79502273054",
                "1213453243", "1234", 123., client1, Employee.TypeEmployee.EDUCATOR);
        employee[0] = employee1;

        Client client2 = new Client("employeeTest2", "pass", Role.EDUCATOR);
        Employee employee2 = new Educator("Alexandr", "Kurkin", "+79502273054",
                "1213453243", "1234", 123, client2, Employee.TypeEmployee.EDUCATOR);
        employee[1] = employee2;

        Client client3 = new Client("employeeTest3", "pass", Role.EDUCATOR);
        Employee employee3 = new Educator("Aleksey", "Isaev", "+79502273054",
                "1213453243", "1234", 123, client3, Employee.TypeEmployee.EDUCATOR);
        employee[2] = employee3;

        Client client4 = new Client("employeeTest4", "pass", Role.EDUCATOR);
        Employee employee4 = new Educator("Aleksey", "Kurkin", "+79502273054",
                "1213453243", "1234", 123, client4, Employee.TypeEmployee.EDUCATOR);
        employee[3] = employee4;

        Client client5 = new Client("employeeTest5", "pass", Role.EDUCATOR);
        Employee employee5 = new Educator("Pavel", "Isaev", "+79502273054",
                "1213453243", "1234", 123, client5, Employee.TypeEmployee.EDUCATOR);
        employee[4] = employee5;

        return employee;
    }

    static Parent[] getListParents() {
        Parent[] parents = new Parent[5];

        Client client1 = new Client("parentTest1", "pass", Role.PARENT);
        Parent parent1 = new Parent("Alexandr", "parent", 'м', "+79502273054", client1, null);
        parents[0] = parent1;

        Client client2 = new Client("parentTest2", "pass", Role.PARENT);
        Parent parent2 = new Parent("Alexandr", "parent", 'м', "+79502273054", client2, null);
        parents[1] = parent2;

        Client client3 = new Client("parentTest3", "pass", Role.PARENT);
        Parent parent3 = new Parent("Aleksey", "parent", 'м', "+79502273054", client3, null);
        parents[2] = parent3;

        Client client4 = new Client("parentTest4", "pass", Role.PARENT);
        Parent parent4 = new Parent("Aleksey", "parent", 'м', "+79502273054", client4, null);
        parents[3] = parent4;

        Client client5 = new Client("parentTest5", "pass", Role.PARENT);
        Parent parent5 = new Parent("Pavel", "parent", 'ж', "+79502273054", client5, null);
        parents[4] = parent5;

        return parents;
    }

    static Children[] getListChildren() {
        Children[] children = new Children[5];

        Client client1 = new Client("childrenTest1", "pass", Role.CHILDREN);
        MedicalBook medicalBook1 = new MedicalBook();
        medicalBook1.setSex('м');
        Children children1 = new Children("Alexandr", "Isaev", medicalBook1,
                null, null, null, client1);
        children[0] = children1;

        Client client2 = new Client("childrenTest2", "pass", Role.CHILDREN);
        MedicalBook medicalBook2 = new MedicalBook();
        medicalBook2.setSex('м');
        Children children2 = new Children("Alexandr", "Kurkin", medicalBook2,
                null, null, null, client2);
        children[1] = children2;

        Client client3 = new Client("childrenTest3", "pass", Role.CHILDREN);
        MedicalBook medicalBook3 = new MedicalBook();
        medicalBook3.setSex('м');
        Children children3 = new Children("Aleksey", "Isaev", medicalBook3,
                null, null, null, client3);
        children[2] = children3;

        Client client4 = new Client("childrenTest4", "pass", Role.CHILDREN);
        MedicalBook medicalBook4 = new MedicalBook();
        medicalBook4.setSex('м');
        Children children4 = new Children("Aleksey", "Kurkin", medicalBook4,
                null, null, null, client4);
        children[3] = children4;

        Client client5 = new Client("childrenTest5", "pass", Role.CHILDREN);
        MedicalBook medicalBook5 = new MedicalBook();
        medicalBook5.setSex('ж');
        Children children5 = new Children("Pavel", "Isaev", medicalBook5,
                null, null, null, client5);
        children[4] = children5;

        return children;
    }

    static String getToken(Credentials credentials) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(credentials.toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
