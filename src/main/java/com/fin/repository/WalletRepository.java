package com.fin.repository;

import com.fin.entity.Wallet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class WalletRepository {
    @PersistenceContext(name = "lotos")
    EntityManager em;

    public Wallet create(Wallet wallet){
        em.persist(wallet);

        return wallet;
    }
}
