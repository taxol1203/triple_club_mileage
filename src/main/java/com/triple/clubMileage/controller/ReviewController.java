package com.triple.clubMileage.controller;

import com.triple.clubMileage.domain.Review;
import com.triple.clubMileage.domain.User;
import com.triple.clubMileage.service.HistoryService;
import com.triple.clubMileage.service.ReviewService;
import com.triple.clubMileage.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final HistoryService historyService;

    @ApiOperation(value = "리뷰 작성, 수정, 삭제", response = HistoryDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 작성 / 수정 / 삭제 성공", response = Integer.class),
    })
    @ApiImplicitParam(name = "action", dataType = "String", value = "ADD : 작성, MOD : 수정, DELETE : 삭제")
    @PostMapping(path = "/event")
    public void eventRequest(@RequestBody ReviewDTO reviewDTO){
        String action = reviewDTO.getAction();

        if(action.equals("ADD")){
            addReview(reviewDTO);
        }else if(action.equals("MOD")){
            modReview(reviewDTO);
        }else{
            eraseReview(reviewDTO.getReviewId());
        }
    }
    /**
     * 리뷰 생성
     *
     * @param reviewDTO
     */
    public void addReview(ReviewDTO reviewDTO) {
        String reviewId = reviewDTO.getReviewId();
        String content = reviewDTO.getContent();
        String userId = reviewDTO.getUserId();
        String placeId = reviewDTO.getPlaceId();
        List<String> photoIds = reviewDTO.getAttachedPhotoIds();

        reviewService.saveReview(reviewId, content, userId, placeId, photoIds);
    }

    /**
     * 리뷰 수정
     * @param reviewDTO
     */
    public void modReview(ReviewDTO reviewDTO) {
        String reviewId = reviewDTO.getReviewId();
        String content = reviewDTO.getContent();
        String userId = reviewDTO.getUserId();
        List<String> photoIds = reviewDTO.getAttachedPhotoIds();

        reviewService.updateReview(reviewId, content, userId, photoIds);
    }

    /**
     * 리뷰 삭제
     * @param reviewId 리뷰 id
     */
    private void eraseReview(String reviewId){
        reviewService.deleteReview(reviewId);
    }

    @ApiOperation(value = "한 사람의 포인트를 가져온다.", response = Integer.class)
    @ApiImplicitParam(name = "user_id", dataType = "String", value = "user id를 입력")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자의 포인트 반환", response = Integer.class),
            @ApiResponse(code = 204, message = "해당하는 id에 따른 사용자가 존재하지 않습니다. ", response = Void.class),
    })
    @GetMapping("/point/{user_id}")
    public ResponseEntity<?> getUserPoint(@PathVariable String user_id) {
        try {
            return userService.getPoint(user_id);
        }catch (Exception e) {
            return new ResponseEntity<String>("Exception: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "한 사람의 포인트 기록을 가져온다.", response = HistoryDTO.class)
    @ApiImplicitParam(name = "user_id", dataType = "String", value = "user id를 입력")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자의 포인트 기록", response = HistoryDTO.class),
            @ApiResponse(code = 204, message = "해당하는 id에 따른 사용자가 존재하지 않습니다. ", response = Void.class),
    })
    @GetMapping("/history/{user_id}")
    public ResponseEntity<?> getUserHistory(@PathVariable String user_id) {
        try {
            User user = userService.getUser(user_id);
            return historyService.getHistoryList(user);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "모든 포인트 부여 기록을 반환한다.", response = Review.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "모든 포인트 부여 기록 반환", response = Review.class),
    })
    @GetMapping("/history")
    public ResponseEntity<?> retrieveAnswer() throws Exception {
        try {
            return historyService.getAllHistoryList();
        }catch (Exception e) {
            return new ResponseEntity<String>("Exception: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
