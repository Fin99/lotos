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
import javax.json.JsonObject;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class EditTest {
    private Credentials chief = new Credentials("chief1@gmail.com", "chief");
    private Parent parent = new Parent();

    {
        parent.setName("Александр");
        parent.setSurname("Исаев");
        parent.setPhoneNumber("88005553535");
        parent.setSex('м');
        parent.setClient(new Client("parentTest", "parentTest", Role.PARENT));
    }

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testNothingEditParent() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " +
                getToken(chief.getUsername(), chief.getPassword()));

        registrationRequest.body(parent.toJson().toString());

        Response response = registrationRequest.post("/registration/parent");
        int registrationStatus = response.getStatusCode();

        assertEquals(registrationStatus, 200);
        ///
        RequestSpecification editRequest = RestAssured.given();
        editRequest.header("Content-Type", "application/json");
        editRequest.header("Authorization", "Bearer " +
                getToken(parent.getClient().getUsername(), parent.getClient().getPassword()));

        JsonObject editParent = Json.createReader(new StringReader(response.getBody().asString())).readObject();
        editParent = Json.createObjectBuilder(editParent)
                .remove("name")
                .add("name", "parentTestEdit").build();

        editRequest.body(editParent.toString());

        int editStatus = editRequest.post("/edit/parent").getStatusCode();

        assertEquals(editStatus, 200);
        ///
        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chief.getUsername(), chief.getPassword()));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", response.jsonPath().getLong("id")).build().toString());

        int removeStatus = removeRequest.post("/remove/parent").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    private String getToken(String username, String password) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(new Credentials(username, password).toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
