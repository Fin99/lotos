package com.fin.controller;

import com.fin.entity.Client;
import com.fin.repository.ClientRepository;
import com.fin.security.Credentials;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.AuthenticationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Path("/auth")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthController {
    @Inject
    ClientRepository clientRepository;

    @Context
    SecurityContext securityContext;

    @POST
    @Path("/authenticate")
    public Response authenticateClient(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {
            Client client = authenticate(username, password);
            String token = issueToken(client);
            JsonObject object = Json.createObjectBuilder().add("token", token).build();
            return Response.ok(object).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @GET
    @Secured
    @Path("/authorization")
    public Response authorizationClient() {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        JsonObject object = Json.createObjectBuilder().add("role", client.getRole().toString()).build();
        return Response.ok(object).build();
    }

    private Client authenticate(String username, String password) throws AuthenticationException {
        Client client = clientRepository.authenticate(new Client(username, password, null));

        if (client == null) {
            throw new AuthenticationException();
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