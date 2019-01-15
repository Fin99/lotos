package com.fin.repository;

import com.fin.entity.Wallet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Named("walletRepository")
public class WalletRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
        em = entityManagerFactory.createEntityManager();
    }


    public Wallet create(Wallet wallet) {
        em.getTransaction().begin();
        em.persist(wallet);
        em.getTransaction().commit();
        return wallet;
    }

    public Wallet find(long id) {
        em.getTransaction().begin();
        Wallet wallet = em.find(Wallet.class, id);
        em.getTransaction().commit();
        return wallet;
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
