package com.fin.repository.place;

import com.fin.entity.Client;
import com.fin.entity.place.Place;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.fin.repository.MainRepository.getElementOrNull;

@Singleton
@Named("placeRepository")
public class PlaceRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lotos");
    private EntityManager em = entityManagerFactory.createEntityManager();

    public Place findPlace(String name){
        String query = "SELECT p FROM Place p WHERE p.name='" + name + "'";
        return getElementOrNull(em.createQuery(query, Place.class).getResultList());
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
