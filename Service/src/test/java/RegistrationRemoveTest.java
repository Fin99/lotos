import com.fin.entity.Children;
import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.employee.Employee;
import com.fin.entity.employee.Teacher;
import com.fin.entity.group.Group;
import com.fin.entity.place.Item;
import com.fin.entity.place.Place;
import com.fin.security.Credentials;
import com.fin.security.Role;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.json.Json;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RegistrationRemoveTest {
    private Credentials chief = new Credentials("chief1@gmail.com", "chief");
    private Parent parent = new Parent();
    private Children children = new Children();
    private Teacher teacher = new Teacher();
    private Place place = new Place();
    private Item item = new Item();
    private Group group = new Group();

    {
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

        group.setName("RomashkaTest");
    }

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void registrationRemoveParent() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken());

        registrationRequest.body(parent.toJson().toString());

        int registrationStatus = registrationRequest.post("/registration/parent").getStatusCode();

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(Json.createObjectBuilder()
                .add("username", parent.getClient().getUsername()).build().toString());

        int removeStatus = removeRequest.post("/remove/parent").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemoveChildren() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken());

        registrationRequest.body(children.toJson().toString());

        int registrationStatus = registrationRequest.post("/registration/children").getStatusCode();

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(Json.createObjectBuilder()
                .add("username", children.getClient().getUsername()).build().toString());

        int removeStatus = removeRequest.post("/remove/children").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemoveEmployee() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken());

        registrationRequest.body(teacher.toJson().toString());

        int registrationStatus = registrationRequest.post("/registration/employee").getStatusCode();

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(Json.createObjectBuilder()
                .add("username", teacher.getClient().getUsername()).build().toString());

        int removeStatus = removeRequest.post("/remove/employee").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemovePlace() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken());

        registrationRequest.body(place.toJson().toString());

        Response response = registrationRequest.post("/registration/place");
        int registrationStatus = response.getStatusCode();

        assertEquals(registrationStatus, 200);

        long idPlace = response.getBody().jsonPath().getLong("id");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idPlace).build().toString());

        int removeStatus = removeRequest.post("/remove/place").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemoveItem() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken());

        registrationRequest.body(item.toJson().toString());

        Response response = registrationRequest.post("/registration/item");
        int registrationStatus = response.getStatusCode();
        long idItem = response.getBody().jsonPath().getLong("id");

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idItem).build().toString());

        int removeStatus = removeRequest.post("/remove/item").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemovePlaceAndItem() {
        RequestSpecification registrationPlaceRequest = RestAssured.given();
        registrationPlaceRequest.header("Content-Type", "application/json");
        registrationPlaceRequest.header("Authorization", "Bearer " + getToken());

        registrationPlaceRequest.body(place.toJson().toString());

        Response responsePlace = registrationPlaceRequest.post("/registration/place");
        int registrationPlaceStatus = responsePlace.getStatusCode();
        assertEquals(registrationPlaceStatus, 200);

        long idPlace = responsePlace.getBody().jsonPath().getLong("id");

        RequestSpecification registrationItemRequest = RestAssured.given();
        registrationItemRequest.header("Content-Type", "application/json");
        registrationItemRequest.header("Authorization", "Bearer " + getToken());

        place.setId(idPlace);
        item.setPlace(place);
        registrationItemRequest.body(item.toJson().toString());

        Response responseItem = registrationItemRequest.post("/registration/item");
        int registrationItemStatus = responseItem.getStatusCode();
        assertEquals(registrationItemStatus, 200);

        long idItem = responseItem.getBody().jsonPath().getLong("id");


        RequestSpecification removeItemRequest = RestAssured.given();
        removeItemRequest.header("Content-Type", "application/json");
        removeItemRequest.header("Authorization", "Bearer " + getToken());

        removeItemRequest.body(Json.createObjectBuilder()
                .add("id", idItem).build().toString());

        int removeItemStatus = removeItemRequest.post("/remove/item").getStatusCode();

        assertEquals(removeItemStatus, 200);

        RequestSpecification removePlaceRequest = RestAssured.given();
        removePlaceRequest.header("Content-Type", "application/json");
        removePlaceRequest.header("Authorization", "Bearer " + getToken());

        removePlaceRequest.body(Json.createObjectBuilder()
                .add("id", idPlace).build().toString());

        int removePlaceStatus = removePlaceRequest.post("/remove/place").getStatusCode();

        assertEquals(removePlaceStatus, 200);

        place.setId(0);
        item.setPlace(null);
    }

    @Test
    public void registrationRemoveGroup() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken());

        registrationRequest.body(group.toJson().toString());

        Response response = registrationRequest.post("/registration/group");
        int registrationStatus = response.getStatusCode();
        long idGroup = response.getBody().jsonPath().getLong("id");

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idGroup).build().toString());

        int removeStatus = removeRequest.post("/remove/group").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    private String getToken() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(chief.toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
