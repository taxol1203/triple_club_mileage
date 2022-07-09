package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.ReviewRepository;
import com.triple.clubMileage.api.RandomStringGenerator;
import com.triple.clubMileage.domain.Photo;
import com.triple.clubMileage.domain.Place;
import com.triple.clubMileage.domain.Review;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
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
     * @param place 장소
     * @return Review List
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
     * @param reviewId  리뷰 id
     * @param content   작성 내용
     * @param userId    유저
     * @param placeId   장소
     * @param photoIds  사진의 id들
     * @return UUID
     * @see UserService#updatePoint(User, int) 
     * @see HistoryService#saveHistory(User, int) 
     */
    @Transactional
    public UUID saveReview(String reviewId, String content, String userId, String placeId, List<String> photoIds) {

        User user = userService.getUser(userId);
        Place place = placeService.getPlace(placeId);

        List<Photo> photoList = addAllPhoto(photoIds);

        boolean hasPhoto = !photoList.isEmpty();
        boolean isFirst = getReviewByPlace(place).isEmpty();

        Review review = Review.creativeReview(reviewId, user, place, content, isFirst, photoList);
        reviewRepository.saveAndFlush(review);

        // 유저의 점수 변동
        userService.updatePoint(user, calPoint(hasPhoto, isFirst));

        return review.getId();
    }

    /**
     * 리뷰 조회
     *
     * @param reviewId 리뷰 id
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
     * @param reviewId 리뷰 id
     */
    @Transactional
    public void deleteReview(String reviewId){

        Review review = getReview(reviewId);
        boolean hasPhoto = !review.getPhotoList().isEmpty();
        boolean isFirst = review.isFirst();

        // 유저의 포인트 변경
        userService.updatePoint(review.getUser(), calPoint(hasPhoto, isFirst) * -1);
        // 리뷰 삭제
        reviewRepository.deleteById(UUID.fromString(reviewId));
    }

    /**
     * 추가될 리뷰에 대한 점수를 계산한다.
     * @param hasImage 리뷰가 이미지를 가지고 있는지
     * @param isFirstPlace 처음 방문한 장소에 대한 리뷰인지
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

    /**
     * 리뷰 수정
     *
     * @param reviewId
     * @param content
     * @param userId
     * @param photoIds
     */
    @Transactional
    public void updateReview(String reviewId, String content, String userId, List<String> photoIds){
        User user = userService.getUser(userId);
        Review review = getReview(reviewId);

        // 기존에 사진을 가지고 있었는지 확인
        boolean hadPhoto = !review.getPhotoList().isEmpty();

        // 우선 연결되어있는 사진을 다 제거한다.
        photoService.deletePhotoByReview(review);
        // 새로 추가될 사진을 저장한다.
        List<Photo> photoList = addAllPhoto(photoIds);

        // 리뷰 수정
        review.setContent(content);
        review.setPhotoList(photoList);

        boolean hasPhoto = !photoList.isEmpty();

        // 유저의 점수 변동
        userService.updatePoint(user, calupdatePoint(hadPhoto, hasPhoto));
    }

    private int calupdatePoint(boolean hadPhoto, boolean hasPhoto) {
        int point = 0;
        if(!hadPhoto && hasPhoto){
            point = 1;
        } else if(hadPhoto && !hasPhoto){
            point = -1;
        }

        return point;
    }

    /**
     * 사진 id들을 받아와서 Photo 객체로 만들고 list에 저장한다.
     *
     * @param photoIds 사진 id
     * @return Photo List
     */
    public List<Photo> addAllPhoto(List<String> photoIds){
        List<Photo> photoList = new ArrayList<>();

        for(String photoId : photoIds){
            Photo photo = Photo.createPhoto(photoId, RandomStringGenerator.getRandomString(15));
            photoList.add(photo);
        }
        return photoList;
    }
}
