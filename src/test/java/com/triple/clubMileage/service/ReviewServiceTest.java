package com.triple.clubMileage.service;

import com.triple.clubMileage.domain.Photo;
import com.triple.clubMileage.domain.Place;
import com.triple.clubMileage.domain.Review;
import com.triple.clubMileage.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired ReviewService reviewService;
    @Autowired PlaceService placeService;
    @Autowired UserService userService;

    @Test
    @Rollback(false)
    public void add_review_test() throws Exception {
        //given
        //Place place = new Place().createPlace(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"), "Busan", 0);
        //User user = new User().createUser(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"), "r")
        String gotID = "b5e31012-ff60-11ec-b939-0242ac120002";

        Place place = placeService.getPlace("a1cc3c30-ff5f-11ec-b939-0242ac120002");
        User user = userService.getUser("8204c5d4-ff5f-11ec-b939-0242ac120002");
        String content = "hi everyone!!!!!4444";

        List<String> photoList = new ArrayList<>();
        photoList.add("b9b64498-ff60-11ec-b939-0242ac120002");
        photoList.add("c1e95678-ff60-11ec-b939-0242ac120002");

        //when
        UUID reviewId = reviewService.saveReview(gotID, content, user.getId().toString(), place.getId().toString(), photoList);

        //then
        Review getReview = reviewService.getReview(reviewId.toString());
        User getUser = userService.getUser("8204c5d4-ff5f-11ec-b939-0242ac120002");
        assertTrue(getReview.isFirst());                        // place에 처음 등록하는 리뷰이므로 first 이어야한다.
        assertEquals(getUser.getPoint(), 3);              // 처음 방문하였으며 사진이 있으므로 3점이다.
    }

    @Test
    public void mod_review_test() throws Exception {
        //given
        String gotID = "b5e31012-ff60-11ec-b939-0242ac120002";

        Review review = reviewService.getReview(gotID);
        User user = review.getUser();
        String newContent = "Wow, Hello world again";
        String userId = user.getId().toString();
        List<String> photoList = new ArrayList<>();
        //when
        reviewService.updateReview(gotID, newContent, userId, photoList);

        //then
        User getUser = userService.getUser(userId);
        Review getReview = reviewService.getReview(gotID);
        assertEquals(newContent,getReview.getContent());
        assertEquals(2,getUser.getPoint());
    }

    @Test
    public void delete_review_test() throws Exception {
        //given
        String gotID = "b5e31012-ff60-11ec-b939-0242ac120002";

        Review review = reviewService.getReview(gotID);
        User user = review.getUser();

        //when
        reviewService.deleteReview(gotID);
        //then
        User getUser = userService.getUser(user.getId().toString());

        assertEquals(0,getUser.getPoint());
    }
}