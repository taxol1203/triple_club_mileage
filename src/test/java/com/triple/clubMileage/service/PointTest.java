package com.triple.clubMileage.service;

import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PointTest {

    @Autowired UserService userService;
    @Autowired HistoryService historyService;

    /**
     * 임의의 User를 생성하고 그 User의 point를 가져오는 테스트
     *
     * @throws Exception
     */
    @Test
    public void get_user_point_test() throws Exception {
        //given

        User user = createUser();
        String userId = user.getId().toString();
        //when

        int point = userService.getPoint(userId);
        //then
        assertEquals(point, user.getPoint());
    }

    /**
     * 임의의 한 사람을 생성하고, 그 사람의 포인트 기록을 생성한다.
     * 이후 해당 인원에 대한
     *
     * @throws Exception
     */
    @Test
    public void get_user_history() throws Exception {
        //given
        User user = createUser();
        String userId = user.getId().toString();
        historyService.saveHistory(user, 3);
        historyService.saveHistory(user, 2);
        historyService.saveHistory(user, 2);
        //when
        List<History> historyList = historyService.getHistoryList(user);
        //then
        assertEquals(3, historyList.size());
    }

    @Test
    public void get_all_history() throws Exception {
        //given

        //when
        List<History> historyList = historyService.getAllHistoryList();
        //then
        assertEquals(4, historyList.size());
    }

    private User createUser(){
        String gotID = "b5e31012-ff60-11ec-b939-0242ac120002";
        userService.saveUser(gotID, "yeri", 7);
        return new User().createUser(UUID.fromString(gotID), "yeri", 7);
    }


}
