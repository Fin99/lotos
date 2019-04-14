package com.fin;

import com.fin.entity.Child;
import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.Parent;
import com.fin.entity.employee.*;
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
    static String url = "http://192.168.0.104";
    static int port = 8080;

    static String urlRegistrationParent = "/registration/parent";
    static String urlRegistrationChildren = "/registration/child";
    static String urlRegistrationEmployee = "/registration/employee";
    static String urlRegistrationItem = "/registration/item";
    static String urlRegistrationPlace = "/registration/place";
    static String urlRegistrationGroup = "/registration/group";

    static String urlRemoveParent = "/remove/parent";
    static String urlRemoveChildren = "/remove/child";
    static String urlRemoveEmployee = "/remove/employee";
    static String urlRemoveItem = "/remove/item";
    static String urlRemovePlace = "/remove/place";
    static String urlRemoveGroup = "/remove/group";

    static String urlFindParent = "/find/parent";
    static String urlFindChildren = "/find/child";
    static String urlFindEmployee = "/find/employee";
    static String urlFindGroup = "/find/group";
    static String urlFindPlace = "/find/place";

    static String urlEditParent = "/edit/parent";
    static String urlEditChildren = "/edit/child";
    static String urlEditEmployee = "/edit/employee";

    static String urlMessageSend = "/message/send";
    static String urlMessageGet = "/message/get";

    static String urlGameStart = "/game/start";

    static Credentials chiefCredentials = new Credentials("chief@gmail.com", "chief");
    static Parent parent = new Parent();
    static Child child = new Child();
    static Teacher teacher = new Teacher();
    static Doctor doctor = new Doctor();
    static Cooker cooker = new Cooker();
    static Place place = new Place();
    static Item item = new Item();
    static Group group = new Group();
    static TypeGroup typeGroup = new TypeGroup();

    static {
        parent.setName("Александр");
        parent.setSurname("Исаев");
        parent.setPhoneNumber("88005553535");
        parent.setSex('м');
        parent.setClient(new Client("parentTest", "parentTest", Role.PARENT));

        child.setName("Александр");
        child.setSurname("Исаев");
        child.setClient(new Client("childrenTest", "childrenTest", Role.CHILD));

        teacher.setPhone("88005553535");
        teacher.setPassport("1212453244");
        teacher.setInn("88005553535");
        teacher.setName("Александр");
        teacher.setSurname("Исаев");
        teacher.setSalary(15000);
        teacher.setTypeEmployee(Employee.TypeEmployee.TEACHER);
        teacher.setClient(new Client("teacherTest", "teacherTest", Role.TEACHER));

        doctor.setInn("88005553535");
        doctor.setName("Александр");
        doctor.setSurname("Исаев");
        doctor.setSalary(15000);
        doctor.setTypeEmployee(Employee.TypeEmployee.DOCTOR);
        doctor.setClient(new Client("teacherTest", "teacherTest", Role.DOCTOR));

        cooker.setPhone("88005553535");
        cooker.setName("Александр");
        cooker.setSalary(15000);
        cooker.setTypeEmployee(Employee.TypeEmployee.COOKER);
        cooker.setClient(new Client("teacherTest", "teacherTest", Role.COOKER));

        place.setName("374TEST");

        item.setName("Pen");
        item.setNote("Red pen");
        item.setPrice(20.3);
        item.setShelfLife(new Date());

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


    static Place[] getListPlace() {
        Place[] places = new Place[5];

        Place place1 = new Place("placeTest1");
        places[0] = place1;

        Place place2 = new Place("placeTest2");
        places[1] = place2;

        Place place3 = new Place("placeTest3");
        places[2] = place3;

        Place place4 = new Place("placeTest4");
        places[3] = place4;

        Place place5 = new Place("placeTest5");
        places[4] = place5;

        return places;
    }

    static Child[] getListChildren() {
        Child[] children = new Child[5];

        Client client1 = new Client("childrenTest1", "pass", Role.CHILD);
        MedicalBook medicalBook1 = new MedicalBook();
        medicalBook1.setSex('м');
        Child child1 = new Child("Alexandr", "Isaev", medicalBook1,
                null, null, null, client1);
        children[0] = child1;

        Client client2 = new Client("childrenTest2", "pass", Role.CHILD);
        MedicalBook medicalBook2 = new MedicalBook();
        medicalBook2.setSex('м');
        Child child2 = new Child("Alexandr", "Kurkin", medicalBook2,
                null, null, null, client2);
        children[1] = child2;

        Client client3 = new Client("childrenTest3", "pass", Role.CHILD);
        MedicalBook medicalBook3 = new MedicalBook();
        medicalBook3.setSex('м');
        Child child3 = new Child("Aleksey", "Isaev", medicalBook3,
                null, null, null, client3);
        children[2] = child3;

        Client client4 = new Client("childrenTest4", "pass", Role.CHILD);
        MedicalBook medicalBook4 = new MedicalBook();
        medicalBook4.setSex('м');
        Child child4 = new Child("Aleksey", "Kurkin", medicalBook4,
                null, null, null, client4);
        children[3] = child4;

        Client client5 = new Client("childrenTest5", "pass", Role.CHILD);
        MedicalBook medicalBook5 = new MedicalBook();
        medicalBook5.setSex('ж');
        Child child5 = new Child("Pavel", "Isaev", medicalBook5,
                null, null, null, client5);
        children[4] = child5;

        return children;
    }


    static Group[] getListGroup() {
        Group[] groups = new Group[5];

        Group group1 = new Group();
        group1.setName("group1");
        group1.setTypeGroup(typeGroup);
        groups[0] = group1;

        Group group2 = new Group();
        group2.setName("group2");
        group2.setTypeGroup(typeGroup);
        groups[1] = group2;

        Group group3 = new Group();
        group3.setName("group3");
        group3.setTypeGroup(typeGroup);
        groups[2] = group3;

        Group group4 = new Group();
        group4.setName("group4");
        group4.setTypeGroup(typeGroup);
        groups[3] = group4;

        Group group5 = new Group();
        group5.setName("group5");
        group5.setTypeGroup(typeGroup);
        groups[4] = group5;

        return groups;
    }

    static String getToken(Credentials credentials) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(credentials.toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
