package com.fin.repository;

import com.fin.entity.money.Refill;
import com.fin.entity.money.Wallet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Named("refillRepository")
public class RefillRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @Inject
    WalletRepository walletRepository;

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
        em = entityManagerFactory.createEntityManager();
    }

    public void refill(Refill refill) {
        Wallet wallet = walletRepository.find(refill.getWallet().getId());

        em.getTransaction().begin();
        if(refill.getStatus().equals(Refill.Status.SATISFIED)){
            wallet.setAccount(wallet.getAccount() + refill.getAmount());
            em.merge(wallet);
        }
        em.persist(refill);
        em.getTransaction().commit();
    }

    @PreDestroy
    public void preDestroy() {
        if (entityManagerFactory.isOpen() && entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        if (em.isOpen() && em != null) {
            em.close();
        }
    }
}
