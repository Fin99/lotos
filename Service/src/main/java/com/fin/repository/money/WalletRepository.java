package com.fin.repository.money;

import com.fin.entity.money.Wallet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Named("walletRepository")
public class WalletRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();


    public void create(Wallet wallet) {
        em.getTransaction().begin();
        em.persist(wallet);
        em.getTransaction().commit();
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
