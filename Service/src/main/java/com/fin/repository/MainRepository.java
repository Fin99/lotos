package com.fin.repository;


import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Singleton
@Named("mainRepository")
public class MainRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public static <T> T getElementOrNull(List<T> resultList) {
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public <T> void create(T entity) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        em.persist(entity);
        em.getTransaction().commit();
    }

    public <T> void update(T entity) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        em.merge(entity);
        em.getTransaction().commit();
    }

    public <T> T find(Class<T> classEntity, long id) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        T entity = em.find(classEntity, id);
        em.getTransaction().commit();
        return entity;
    }

    public <T> void remove(Class<T> classEntity, long id) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        T entityRef = em.find(classEntity, id);
        em.remove(entityRef);
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
