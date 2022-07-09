package com.triple.clubMileage.service;

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
        String gotID = "240a0658-dc5f-4878-9381-ebb7b2667774";
        //reviewService.deleteReview(gotID);

        Place place = placeService.getPlace("2e4baf1c-5acb-4efb-a1af-eddada31b00a");
        User user = userService.getUser("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        String content = "hi everyone!!!!!33333";

        List<String> photoList = new ArrayList<>();
        photoList.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bc3");
        photoList.add("afb0cef2-851d-4a50-bb07-9cc15cbdc343");

        //when
        UUID reviewId = reviewService.saveReview(gotID, content, user.getId().toString(), place.getId().toString(), photoList);
        //then
//        Review getReview = reviewService.getReview(reviewId.toString());
//        assertTrue(getReview.isFirst());                        // place에 처음 등록하는 리뷰이므로 first 이어야한다.

    }


}