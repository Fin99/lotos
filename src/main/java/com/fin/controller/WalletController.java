package com.fin.controller;

import com.fin.entity.Wallet;
import com.fin.repository.WalletRepository;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/wallet")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WalletController {

    @Inject
    WalletRepository walletRepository;

    @POST
    @Secured
    @Path("/add")
    public JsonObject save() {
        return this.walletRepository.create(new Wallet()).toJson();
    }

    @GET
    @Secured
    @Path("/get/{indexWallet}")
    public JsonObject get(@PathParam("indexWallet") long indexWallet) {
        return walletRepository.find(indexWallet).toJson();
    }
}