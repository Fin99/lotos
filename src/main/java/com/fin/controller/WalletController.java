package com.fin.controller;

import com.fin.entity.Wallet;
import com.fin.repository.WalletRepository;
import com.fin.security.Secured;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/wallet")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WalletController {

    @Inject
    WalletRepository walletRepository;

    @POST
    @Path("/add")
    public JsonObject save(@Valid Wallet wal) {
        return this.walletRepository.create(wal).toJson();
    }

    @GET
    @Secured
    @Path("/get/{indexWallet}")
    public JsonObject get(@PathParam("indexWallet") long indexWallet) {
        return walletRepository.find(indexWallet).toJson();
    }
}