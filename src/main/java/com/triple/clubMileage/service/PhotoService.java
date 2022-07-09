package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.PhotoRepository;
import com.triple.clubMileage.api.RandomStringGenerator;
import com.triple.clubMileage.domain.Photo;
import com.triple.clubMileage.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;


    @Transactional
    public UUID savePhoto(String photoId){
        Photo photo = Photo.createPhoto(photoId, RandomStringGenerator.getRandomString(15));
        photoRepository.save(photo);

        return photo.getId();
    }

    @Transactional
    public void deletePhotoByReview(Review review){
        photoRepository.deletePhotoByReview(review);
    }
}
