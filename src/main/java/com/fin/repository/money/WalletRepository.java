package com.fin.repository.money;

import com.fin.entity.money.Wallet;
import com.fin.repository.Repository;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("walletRepository")
public class WalletRepository extends Repository {

    public void create(Wallet wallet) {
        getEntityManager().getTransaction().begin(); // FIXME delete me (main repository)
        getEntityManager().persist(wallet);
        getEntityManager().getTransaction().commit();
    }

    public Wallet find(long id) {
        getEntityManager().getTransaction().begin();
        Wallet wallet = getEntityManager().find(Wallet.class, id);
        getEntityManager().getTransaction().commit();
        return wallet;
    }

}
