package com.fin.repository;

import com.fin.config.DatabaseConfig;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class Repository {
    private EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory(DatabaseConfig.PERSISTENCE_UNIT_NAME);
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public int hashCode() {
        return Objects.hash(entityManagerFactory, entityManager);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PreDestroy
    public void preDestroy() {
        if (entityManagerFactory.isOpen() && entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        if (entityManager.isOpen() && entityManager != null) {
            entityManager.close();
        }
    }
}
