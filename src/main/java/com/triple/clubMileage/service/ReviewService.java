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

/**
 * 리뷰 서비스
 * 리뷰 저장 / 삭제 / 수정 을 한다.
 * @author taxol
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final PhotoService photoService;

    /**
     * 주어진 장소에 대한 작성한 리뷰를 가져온다.
     *
     * @param place
     * @return List
     */
    @Transactional(readOnly = true)
    public List<Review> getReviewByPlace(Place place){
        Optional<List<Review>> reviewList = reviewRepository.findByPlace(place);
        return reviewList.orElse(null);
    }

    /**
     * 리뷰 저장
     *
     * 리뷰를 저장 할 시, 계산된 point를 작성한 user에 등록한다.
     * 또한 리뷰 내역을 추가한다.
     *
     * @param reviewId
     * @param content
     * @param userId
     * @param placeId
     * @param photoIds
     * @return UUID
     * @see UserService#updatePoint(User, int) 
     * @see HistoryService#saveHistory(User, int) 
     */
    @Transactional
    public UUID saveReview(String reviewId, String content, String userId, String placeId, List<String> photoIds) {

        User user = userService.getUser(userId);
        Place place = placeService.getPlace(placeId);

        List<Photo> photoList = new ArrayList<>();
        for(String photoId : photoIds){
            Photo photo = Photo.createPhoto(photoId, RandomStringGenerator.getRandomString(15));
            photoList.add(photo);
        }

        boolean hasPhoto = !photoList.isEmpty();
        boolean isFirst = getReviewByPlace(place).isEmpty();

        Review review = Review.creativeReview(reviewId, user, place, content, isFirst, photoList);
        reviewRepository.save(review);

        // 유저의 점수 변동
        userService.updatePoint(user, calPoint(hasPhoto, isFirst));

        return review.getId();
    }

    /**
     * 리뷰 조회
     *
     * @param reviewId
     * @return Review
     */
    @Transactional(readOnly = true)
    public Review getReview(String reviewId){
        Optional<Review> reviewOptional = reviewRepository.findById(UUID.fromString(reviewId));
        Review review;
        if(reviewOptional.isPresent()){
            review = reviewOptional.get();
        } else {
            throw new EntityNotFoundException(
                    "Cant find any review under given reviewId");
        }
        return review;
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     */
    @Transactional
    public void deleteReview(String reviewId){
        reviewRepository.deleteById(UUID.fromString(reviewId));
    }

    /**
     * 추가될 리뷰에 대한 점수를 계산한다.
     * @param hasImage
     * @param isFirstPlace
     * @return point
     */
    public int calPoint(boolean hasImage, boolean isFirstPlace){
        int point = 1;
        if(hasImage)
            point++;
        if(isFirstPlace)
            point++;
        return point;
    }
}
