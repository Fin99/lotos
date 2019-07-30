package com.fin.repository.money;

import com.fin.entity.money.Refill;
import com.fin.entity.money.Wallet;
import com.fin.repository.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("refillRepository")
public class RefillRepository extends Repository {
    @Inject
    WalletRepository walletRepository;

    public void refill(Refill refill) {
        Wallet wallet = walletRepository.find(refill.getWallet().getId());

        getEntityManager().getTransaction().begin();
        if (refill.getStatus().equals(Refill.Status.SATISFIED)) {
            wallet.setAccount(wallet.getAccount() + refill.getAmount());
            getEntityManager().merge(wallet);
        }
        getEntityManager().persist(refill);
        getEntityManager().getTransaction().commit();
    }

}
