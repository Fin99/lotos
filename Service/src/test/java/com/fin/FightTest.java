package com.fin;

import com.fin.entity.Child;
import com.fin.entity.Jsonable;
import com.fin.entity.game.Fight;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.fin.Data.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FightTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.url;
        RestAssured.port = Data.port;
    }

    @Test
    public void testFightStart() {
        Long[] teacherId = createEntity(urlRegistrationEmployee, teacher);

        RequestSpecification fightRequest = RestAssured.given();
        fightRequest.header("Content-Type", "application/json");
        fightRequest.header("Authorization", "Bearer " +
                getToken(teacher.getClient().getCredentials()));

        List<Child> children = new ArrayList<>();
        Child[] childArray = getListChildren();
        children.add(childArray[0]);
        children.add(childArray[1]);

        fightRequest.body(Jsonable.wrapList(children).toString());
        Response response = fightRequest.post(urlGameStart);

        JsonPath jsonPath = new JsonPath(response.body().asString());
        Fight fight = jsonPath.getObject("", Fight.class);

        assertNotEquals(null, fight);
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        removeEntity(urlRemoveEmployee, teacherId);
    }
}
