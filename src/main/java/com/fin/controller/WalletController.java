package com.fin.controller;

import com.fin.entity.Wallet;
import com.fin.repository.WalletRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("wallet")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WalletController {

    @Inject
    WalletRepository walletRepository;

    @POST
    public JsonObject save(@Valid Wallet wal) {
        return this.walletRepository.create(wal).toJson();
    }
}