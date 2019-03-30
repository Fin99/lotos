import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Jsonable;
import com.fin.entity.Parent;
import com.fin.entity.employee.Educator;
import com.fin.entity.employee.Employee;
import com.fin.entity.medical.MedicalBook;
import com.fin.security.Credentials;
import com.fin.security.Role;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FindTest {
    private Credentials chief = new Credentials("chief1@gmail.com", "chief");

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testParents() {
        List<Long> parentsId = createEntity(getListParents(), "/registration/parent");
        Parent parent = new Parent();
        Client client = new Client("Test", "", Role.PARENT);
        parent.setClient(client);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(parent.toJson().toString());

        Response response = removeRequest.post("/find/parent");
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 5);

        removeEntity(parentsId, "/remove/parent");
    }

    @Test
    public void testChildren() {
        List<Long> childrenId = createEntity(getListChildren(), "/registration/children");

        MedicalBook medicalBook = new MedicalBook();
        medicalBook.setSex('ж');
        Children child = new Children();
        child.setMedicalBook(medicalBook);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(child.toJson().toString());

        Response response = removeRequest.post("/find/children");
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 1);

        removeEntity(childrenId, "/remove/children");
    }

    @Test
    public void testEmployee() {
        List<Long> employeeId = createEntity(getListEmployee(), "/registration/employee");

        Employee employee = new Educator();
        employee.setTypeEmployee(Employee.TypeEmployee.EDUCATOR);
        employee.setName("Pavel");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(employee.toJson().toString());

        Response response = removeRequest.post("/find/employee");
        assertEquals(response.getStatusCode(), 200);

        List<Object> jsonPath = response.body().jsonPath().getList("");
        assertEquals(jsonPath.size(), 1);

        removeEntity(employeeId, "/remove/employee");
    }

    private void removeEntity(List<Long> idList, String url) {
        for (Long id : idList) {
            RequestSpecification removeRequest = RestAssured.given();
            removeRequest.header("Content-Type", "application/json");
            removeRequest.header("Authorization", "Bearer " + getToken());

            removeRequest.body(Json.createObjectBuilder()
                    .add("id", id).build().toString());

            assertEquals(removeRequest.post(url).statusCode(), 200);
        }
    }

    private <T extends Jsonable> List<Long> createEntity(List<T> entities, String url) {
        List<Long> idList = new ArrayList<>();
        for (Jsonable entity : entities) {
            RequestSpecification registrationRequest = RestAssured.given();
            registrationRequest.header("Content-Type", "application/json");
            registrationRequest.header("Authorization", "Bearer " + getToken());

            registrationRequest.body(entity.toJson().toString());

            Response response = registrationRequest.post(url);
            assertEquals(response.statusCode(), 200);

            long id = response.getBody().jsonPath().getLong("id");
            idList.add(id);
        }
        return idList;
    }

    private List<Employee> getListEmployee() {
        List<Employee> employee = new ArrayList<>();

        Client client1 = new Client("employeeTest1", "pass", Role.EDUCATOR);
        Employee employee1 = new Educator("Alexandr", "Isaev", "+79502273054",
                "1213453243", "1234", 123., client1, Employee.TypeEmployee.EDUCATOR);
        employee.add(employee1);

        Client client2 = new Client("employeeTest2", "pass", Role.EDUCATOR);
        Employee employee2 = new Educator("Alexandr", "Kurkin", "+79502273054",
                "1213453243", "1234", 123, client2, Employee.TypeEmployee.EDUCATOR);
        employee.add(employee2);

        Client client3 = new Client("employeeTest3", "pass", Role.EDUCATOR);
        Employee employee3 = new Educator("Aleksey", "Isaev", "+79502273054",
                "1213453243", "1234", 123, client3, Employee.TypeEmployee.EDUCATOR);
        employee.add(employee3);

        Client client4 = new Client("employeeTest4", "pass", Role.EDUCATOR);
        Employee employee4 = new Educator("Aleksey", "Kurkin", "+79502273054",
                "1213453243", "1234", 123, client4, Employee.TypeEmployee.EDUCATOR);
        employee.add(employee4);

        Client client5 = new Client("employeeTest5", "pass", Role.EDUCATOR);
        Employee employee5 = new Educator("Pavel", "Isaev", "+79502273054",
                "1213453243", "1234", 123, client5, Employee.TypeEmployee.EDUCATOR);
        employee.add(employee5);

        return employee;
    }

    private List<Parent> getListParents() {
        List<Parent> parents = new ArrayList<>();

        Client client1 = new Client("parentTest1", "pass", Role.PARENT);
        Parent parent1 = new Parent("Alexandr", "parent", 'м', "+79502273054", client1, null);
        parents.add(parent1);

        Client client2 = new Client("parentTest2", "pass", Role.PARENT);
        Parent parent2 = new Parent("Alexandr", "parent", 'м', "+79502273054", client2, null);
        parents.add(parent2);

        Client client3 = new Client("parentTest3", "pass", Role.PARENT);
        Parent parent3 = new Parent("Aleksey", "parent", 'м', "+79502273054", client3, null);
        parents.add(parent3);

        Client client4 = new Client("parentTest4", "pass", Role.PARENT);
        Parent parent4 = new Parent("Aleksey", "parent", 'м', "+79502273054", client4, null);
        parents.add(parent4);

        Client client5 = new Client("parentTest5", "pass", Role.PARENT);
        Parent parent5 = new Parent("Pavel", "parent", 'ж', "+79502273054", client5, null);
        parents.add(parent5);

        return parents;
    }

    private List<Children> getListChildren() {
        List<Children> children = new ArrayList<>();

        Client client1 = new Client("childrenTest1", "pass", Role.CHILDREN);
        MedicalBook medicalBook1 = new MedicalBook();
        medicalBook1.setSex('м');
        Children children1 = new Children("Alexandr", "Isaev", medicalBook1,
                null, null, null, client1);
        children.add(children1);

        Client client2 = new Client("childrenTest2", "pass", Role.CHILDREN);
        MedicalBook medicalBook2 = new MedicalBook();
        medicalBook2.setSex('м');
        Children children2 = new Children("Alexandr", "Kurkin", medicalBook2,
                null, null, null, client2);
        children.add(children2);

        Client client3 = new Client("childrenTest3", "pass", Role.CHILDREN);
        MedicalBook medicalBook3 = new MedicalBook();
        medicalBook3.setSex('м');
        Children children3 = new Children("Aleksey", "Isaev", medicalBook3,
                null, null, null, client3);
        children.add(children3);

        Client client4 = new Client("childrenTest4", "pass", Role.CHILDREN);
        MedicalBook medicalBook4 = new MedicalBook();
        medicalBook4.setSex('м');
        Children children4 = new Children("Aleksey", "Kurkin", medicalBook4,
                null, null, null, client4);
        children.add(children4);

        Client client5 = new Client("childrenTest5", "pass", Role.CHILDREN);
        MedicalBook medicalBook5 = new MedicalBook();
        medicalBook5.setSex('ж');
        Children children5 = new Children("Pavel", "Isaev", medicalBook5,
                null, null, null, client5);
        children.add(children5);

        return children;
    }

    private String getToken() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(chief.toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
