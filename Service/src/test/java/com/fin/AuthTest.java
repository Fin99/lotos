package com.fin;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.fin.Data.getToken;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class AuthTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.url;
        RestAssured.port = Data.port;
    }

    @Test
    public void testAuthenticate() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(Data.chiefCredentials.toJson().toString());
        System.out.println(Data.chiefCredentials.toJson());

        int status = request.post("/auth/authenticate").getStatusCode();

        assertEquals(status, 200);
    }

    @Test
    public void testAuthorization() {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        request.get("/auth/authorization").then()
            .body("role", equalTo("CHIEF"));
    }
}
