package com.fin;

import com.fin.entity.Parent;
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

        int editStatus = editRequest.post("/edit/parent").getStatusCode();

        assertEquals(editStatus, 200);

        // find this parent
        Parent parent = new Parent();
        parent.setName("parentTestEdit");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(chiefCredentials));
        removeRequest.body(parent.toJson().toString());
        Response response = removeRequest.post(urlFindParent);
        List<Object> jsonPath = response.body().jsonPath().getList("");

        //check change
        assertEquals(jsonPath.size(), 1);

        removeEntity(urlRemoveParent, idParent);
    }

    @Test
    public void testNothingEditParent() {

    }


}
