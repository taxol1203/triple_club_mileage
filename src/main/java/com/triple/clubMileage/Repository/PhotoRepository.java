package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.Photo;
import com.triple.clubMileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    void deletePhotoByReview(Review review);
}
