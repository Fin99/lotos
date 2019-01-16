package com.fin.controller;

import com.fin.entity.Client;
import com.fin.repository.ClientRepository;
import com.fin.security.Credentials;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Path("/authentication")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationController {

    @Inject
    ClientRepository clientRepository;

    @POST
    @Path("/registration")
    public Response registrationClient(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        if (clientRepository.isExist(username)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        Client client = clientRepository.create(new Client(username, password));

        String token = issueToken(client);

        return Response.ok(Json.createObjectBuilder().add("token", token).build()).build();
    }

    @POST
    @Path("/authenticate")
    public Response authenticateClient(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {

            Client client = authenticate(username, password);

            String token = issueToken(client);

            return Response.ok(Json.createObjectBuilder().add("token", token).build()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private Client authenticate(String username, String password) throws Exception {
        Client client = clientRepository.authenticate(new Client(username, password));

        if (client == null) {
            throw new Exception();
        }

        return client;
    }

    private String issueToken(Client client) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);

        client.setToken(token);

        clientRepository.update(client);

        return token;
    }
}