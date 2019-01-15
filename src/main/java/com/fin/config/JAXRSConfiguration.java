package com.fin.config;

import com.fin.controller.WalletController;
import com.fin.entity.Wallet;
import com.fin.repository.WalletRepository;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class JAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> h = new HashSet<>();
        h.add(WalletController.class);
        h.add(WalletRepository.class);
        return h;
    }
}
