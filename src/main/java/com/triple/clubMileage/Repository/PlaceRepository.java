package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {
}
