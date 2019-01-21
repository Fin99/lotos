package com.fin.controller;

import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.Wallet;
import com.fin.repository.ClientRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.WalletRepository;
import com.fin.security.Credentials;
import com.fin.security.Role;

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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Path("/authentication")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationController {
    @Inject
    ClientRepository clientRepository;

    @Inject
    ParentRepository parentRepository;

    @Inject
    WalletRepository walletRepository;

    @POST
    @Path("/registration/parent")
    public Response registrationParent(Parent parent) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.PARENT);
        parent.getClient().setRoles(roles);

        if (clientRepository.findByUsername(parent.getClient().getUsername()) == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        clientRepository.create(parent.getClient());

        Wallet wallet = new Wallet(0);
        walletRepository.create(wallet);
        parent.setWallet(wallet);

        parentRepository.create(parent);

        String token = issueToken(parent.getClient());
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
        Client client = clientRepository.authenticate(new Client(username, password, null));

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