package com.fin.controller;

import com.fin.entity.Client;
import com.fin.entity.Parent;
import com.fin.entity.money.Refill;
import com.fin.entity.money.Wallet;
import com.fin.repository.ClientRepository;
import com.fin.repository.ParentRepository;
import com.fin.repository.money.RefillRepository;
import com.fin.repository.money.WalletRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/wallet")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WalletController {
    @Inject
    ParentRepository parentRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    RefillRepository refillRepository;
    @Inject
    WalletRepository walletRepository;

    @Context
    SecurityContext securityContext;

    @GET
    @Secured({Role.PARENT})
    @Path("/get")
    public Response get() {
        Client client = clientRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Parent parent = parentRepository.findByClient(client);

        Wallet wallet = parent.getWallet();
        if (wallet != null) {
            return Response.ok(wallet.toJson()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Secured({Role.ADMIN})
    @Path("/refill")
    public Response create(Wallet wallet) {
        walletRepository.create(wallet);
        return Response.ok(wallet.toJson()).build();
    }

    @POST
    @Secured({Role.ADMIN})
    @Path("/refill")
    public Response refill(Refill refill) {
        refillRepository.refill(refill);
        return Response.ok().build();
    }
}