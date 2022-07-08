package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.Place;
import com.triple.clubMileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<List<Review>> findByPlace(Place place);
}
