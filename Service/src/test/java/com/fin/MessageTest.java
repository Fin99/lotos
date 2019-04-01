package com.fin;

import com.fin.entity.Client;
import com.fin.entity.Message;
import com.fin.security.Credentials;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.fin.Data.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.url;
        RestAssured.port = Data.port;
    }

    @Test
    public void testMessage() {
        Client sender = new Client();
        sender.setId(1);
        Client receiver = new Client();
        receiver.setId(3);

        Message message = new Message(sender, receiver, "HelloWorld", new Date());

        RequestSpecification sendMessage = RestAssured.given();
        sendMessage.header("Content-Type", "application/json");
        sendMessage.header("Authorization", "Bearer " + getToken(chiefCredentials));

        sendMessage.body(message.toJson().toString());

        Response sendResponse = sendMessage.post(urlMessageSend);
        assertEquals(sendResponse.getStatusCode(), 200);

        RequestSpecification receiveMessage = RestAssured.given();
        receiveMessage.header("Content-Type", "application/json");
        receiveMessage.header("Authorization", "Bearer " + getToken(new Credentials("chief1@gmail.com", "chief")));

        receiveMessage.body(receiver.toJson().toString());

        Response receiveResponse = receiveMessage.post(urlMessageGet);
        assertEquals(receiveResponse.getStatusCode(), 200);

        List<Object> jsonPath = receiveResponse.body().jsonPath().getList("");
        assertTrue(jsonPath.size() > 1);
    }
}
