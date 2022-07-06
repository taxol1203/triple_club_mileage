package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    private final EntityManager em;

    public void save(Place place){
        em.persist(place);
    }

}
