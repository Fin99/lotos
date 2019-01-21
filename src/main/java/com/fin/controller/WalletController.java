package com.fin.controller;

import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.Wallet;
import com.fin.repository.ClientRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.WalletRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/wallet")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WalletController {
    @Inject
    ParentRepository parentRepository;
    @Inject
    ClientRepository clientRepository;

    @Context
    SecurityContext securityContext;

    @GET
    @Secured({Role.PARENT})
    @Path("/get")
    public JsonObject get() {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Parent parent = parentRepository.findByClient(client);

        Wallet wallet = parent.getWallet();
        if (wallet != null) {
            return wallet.toJson();
        } else {
            return Json.createObjectBuilder().build();
        }
    }
}