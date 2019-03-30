package com.fin;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.json.Json;

import static com.fin.Data.getToken;
import static org.junit.Assert.assertEquals;

public class RegistrationRemoveTest {


    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.url;
        RestAssured.port = Data.port;
    }

    @Test
    public void registrationRemoveParent() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationRequest.body(Data.parent.toJson().toString());

        Response response = registrationRequest.post("/registration/parent");
        int registrationStatus = response.getStatusCode();

        assertEquals(registrationStatus, 200);

        long idParent = response.getBody().jsonPath().getLong("id");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idParent).build().toString());

        int removeStatus = removeRequest.post("/remove/parent").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemoveChildren() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationRequest.body(Data.children.toJson().toString());

        Response response = registrationRequest.post("/registration/children");
        int registrationStatus = response.getStatusCode();

        assertEquals(registrationStatus, 200);

        long childrenId = response.getBody().jsonPath().getLong("id");


        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", childrenId).build().toString());

        int removeStatus = removeRequest.post("/remove/children").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemoveEmployee() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationRequest.body(Data.teacher.toJson().toString());

        Response response = registrationRequest.post("/registration/employee");
        int registrationStatus = response.getStatusCode();

        assertEquals(registrationStatus, 200);

        long employeeId = response.getBody().jsonPath().getLong("id");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", employeeId).build().toString());

        int removeStatus = removeRequest.post("/remove/employee").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemovePlace() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationRequest.body(Data.place.toJson().toString());

        Response response = registrationRequest.post("/registration/place");
        int registrationStatus = response.getStatusCode();

        assertEquals(registrationStatus, 200);

        long idPlace = response.getBody().jsonPath().getLong("id");

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idPlace).build().toString());

        int removeStatus = removeRequest.post("/remove/place").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemoveItem() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationRequest.body(Data.item.toJson().toString());

        Response response = registrationRequest.post("/registration/item");
        int registrationStatus = response.getStatusCode();
        long idItem = response.getBody().jsonPath().getLong("id");

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idItem).build().toString());

        int removeStatus = removeRequest.post("/remove/item").getStatusCode();

        assertEquals(removeStatus, 200);
    }

    @Test
    public void registrationRemovePlaceAndItem() {
        RequestSpecification registrationPlaceRequest = RestAssured.given();
        registrationPlaceRequest.header("Content-Type", "application/json");
        registrationPlaceRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationPlaceRequest.body(Data.place.toJson().toString());

        Response responsePlace = registrationPlaceRequest.post("/registration/place");
        int registrationPlaceStatus = responsePlace.getStatusCode();
        assertEquals(registrationPlaceStatus, 200);

        long idPlace = responsePlace.getBody().jsonPath().getLong("id");

        RequestSpecification registrationItemRequest = RestAssured.given();
        registrationItemRequest.header("Content-Type", "application/json");
        registrationItemRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        Data.place.setId(idPlace);
        Data.item.setPlace(Data.place);
        registrationItemRequest.body(Data.item.toJson().toString());

        Response responseItem = registrationItemRequest.post("/registration/item");
        int registrationItemStatus = responseItem.getStatusCode();
        assertEquals(registrationItemStatus, 200);

        long idItem = responseItem.getBody().jsonPath().getLong("id");


        RequestSpecification removeItemRequest = RestAssured.given();
        removeItemRequest.header("Content-Type", "application/json");
        removeItemRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeItemRequest.body(Json.createObjectBuilder()
                .add("id", idItem).build().toString());

        int removeItemStatus = removeItemRequest.post("/remove/item").getStatusCode();

        assertEquals(removeItemStatus, 200);

        RequestSpecification removePlaceRequest = RestAssured.given();
        removePlaceRequest.header("Content-Type", "application/json");
        removePlaceRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removePlaceRequest.body(Json.createObjectBuilder()
                .add("id", idPlace).build().toString());

        int removePlaceStatus = removePlaceRequest.post("/remove/place").getStatusCode();

        assertEquals(removePlaceStatus, 200);

        Data.place.setId(0);
        Data.item.setPlace(null);
    }

    @Test
    public void registrationRemoveGroup() {
        RequestSpecification registrationRequest = RestAssured.given();
        registrationRequest.header("Content-Type", "application/json");
        registrationRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        registrationRequest.body(Data.group.toJson().toString());

        Response response = registrationRequest.post("/registration/group");
        int registrationStatus = response.getStatusCode();
        long idGroup = response.getBody().jsonPath().getLong("id");

        assertEquals(registrationStatus, 200);

        RequestSpecification removeRequest = RestAssured.given();
        removeRequest.header("Content-Type", "application/json");
        removeRequest.header("Authorization", "Bearer " + getToken(Data.chiefCredentials));

        removeRequest.body(Json.createObjectBuilder()
                .add("id", idGroup).build().toString());

        int removeStatus = removeRequest.post("/remove/group").getStatusCode();

        assertEquals(removeStatus, 200);
    }
}
