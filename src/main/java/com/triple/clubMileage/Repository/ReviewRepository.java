package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }


}
