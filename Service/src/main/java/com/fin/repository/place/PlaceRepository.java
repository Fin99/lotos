package com.fin.repository.place;

import com.fin.entity.place.Place;
import com.fin.repository.Repository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("placeRepository")
public class PlaceRepository extends Repository {

    public List<Place> findPlace(Place placeData) {
        String query = "SELECT p FROM Place p WHERE p.name LIKE '%" + placeData.getName() + "%'";

        return getEntityManager().createQuery(query, Place.class).getResultList();
    }
}
