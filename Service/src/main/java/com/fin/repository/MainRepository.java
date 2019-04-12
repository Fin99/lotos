package com.fin.repository;


import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("mainRepository")
public class MainRepository extends Repository {

    public static <T> T getElementOrNull(List<T> resultList) {
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public <T> void create(T entity) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    public <T> void update(T entity) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit();
    }

    public <T> T find(Class<T> classEntity, long id) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        T entity = getEntityManager().find(classEntity, id);
        getEntityManager().getTransaction().commit();
        return entity;
    }

    public <T> void remove(Class<T> classEntity, long id) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        T entityRef = getEntityManager().find(classEntity, id);
        getEntityManager().remove(entityRef);
        getEntityManager().getTransaction().commit();
    }

}
