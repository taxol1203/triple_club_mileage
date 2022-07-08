package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.PhotoRepository;
import com.triple.clubMileage.Repository.PlaceRepository;
import com.triple.clubMileage.Repository.ReviewRepository;
import com.triple.clubMileage.Repository.UserRepository;
import com.triple.clubMileage.api.RandomStringGenerator;
import com.triple.clubMileage.domain.Photo;
import com.triple.clubMileage.domain.Place;
import com.triple.clubMileage.domain.Review;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    ReviewRepository reviewRepository;
    UserService userService;
    PlaceService placeService;
    PhotoService photoService;

    @Transactional
    public List<Review> getReviewByPlace(Place place){
        Optional<List<Review>> reviewList = reviewRepository.findByPlace(place);
        return reviewList.orElse(null);
    }

    @Transactional
    public UUID saveReview(String reviewId, String content, String userId, String placeId, List<String> photoIds) {

        User user = userService.getUser(userId);
        Place place = placeService.getPlace(placeId);

        List<Photo> photoList = new ArrayList<>();
        for(String photoId : photoIds){
            Photo photo = Photo.createPhoto(photoId, RandomStringGenerator.getRandomString(15));
            photoList.add(photo);
        }
        boolean isFirst = getReviewByPlace(place) == null;

        Review review = Review.creativeReview(reviewId, user, place, content, isFirst, photoList);
        reviewRepository.save(review);

        return review.getId();
    }
}
