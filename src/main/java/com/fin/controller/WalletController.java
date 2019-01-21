package com.fin.controller;

import com.fin.entity.Wallet;
import com.fin.repository.WalletRepository;
import com.fin.security.Role;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/wallet")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WalletController {

    @Inject
    WalletRepository walletRepository;

    @GET
    @Secured({Role.PARENT})
    @Path("/get/{indexWallet}")
    public JsonObject get(@PathParam("indexWallet") long indexWallet) {
        Wallet wallet = walletRepository.find(indexWallet);
        if (wallet != null) {
            return wallet.toJson();
        } else {
            return Json.createObjectBuilder().build();
        }
    }
}