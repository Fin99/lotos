import com.fin.security.Credentials;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class AuthTest {
    private Credentials chief = new Credentials("chief1@gmail.com", "chief");

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testAuthenticate() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        System.out.println(chief.toJson());
        request.body(chief.toJson().toString());

        int status = request.post("/auth/authenticate").getStatusCode();

        assertEquals(status, 200);
    }

    @Test
    public void testAuthorization() {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + getToken());

        request.get("/auth/authorization").then()
                .body("role", equalTo("CHIEF"));
    }

    private String getToken() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        request.body(chief.toJson().toString());

        Response response = request.post("/auth/authenticate");

        return response.jsonPath().get("token");
    }
}
