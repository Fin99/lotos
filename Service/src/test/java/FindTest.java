import com.fin.entity.Client;
import com.fin.entity.Parent;
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
        List<Parent> parents = createParents();
        Parent parent = new Parent();
        Client client = new Client("Test", "", Role.PARENT);
        parent.setClient(client);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken());

        System.out.println(parent.toJson());
        removeRequest.body(parent.toJson().toString());

        Response response = removeRequest.post("/find/parent");
        List<Object> jsonPath = response.body().jsonPath().getList("");
        System.out.println(response.body().asString());
        assertEquals(jsonPath.size(), 5);

        removeParents(parents);
    }

    private void removeParents(List<Parent> parents) {
        for (Parent parent : parents) {
            RequestSpecification removeRequest = RestAssured.given();
            removeRequest.header("Content-Type", "application/json");
            removeRequest.header("Authorization", "Bearer " + getToken());

            removeRequest.body(Json.createObjectBuilder()
                    .add("id", parent.getId()).build().toString());

            removeRequest.post("/remove/parent");
        }
    }

    private List<Parent> createParents() {
        List<Parent> parents = getListParents();
        for (Parent parent : parents) {
            RequestSpecification registrationRequest = RestAssured.given();
            registrationRequest.header("Content-Type", "application/json");
            registrationRequest.header("Authorization", "Bearer " + getToken());

            registrationRequest.body(parent.toJson().toString());

            long idParent = registrationRequest.post("/registration/parent").getBody().jsonPath().getLong("id");
            parent.setId(idParent);
        }
        return parents;
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

    private String getToken() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(chief.toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
