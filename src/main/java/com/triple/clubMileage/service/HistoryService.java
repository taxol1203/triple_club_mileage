package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.HistoryRepository;
import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.Review;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional
    public Long saveHistory(User user, int point){
        History history = new History().createHistory(user, point);
        historyRepository.save(history);
        return history.getId();
    }

    /**
     * 개인에 대한 포인트 부여 기록을 가져온다
     *
     * @param user 사용자
     * @return 포인트 부여 기록 List
     */
    @Transactional(readOnly = true)
    public List<History> getHistoryList(User user){
        Optional<List<History>> historyList = historyRepository.findByUser(user);
        return historyList.orElse(null);
    }

    /**
     * 전체 포인트 부여 기록을 가져온다.
     *
     * @return 포인트 부여 기록 List
     */
    @Transactional(readOnly = true)
    public List<History> getAllHistoryList(){
        return historyRepository.findAll();
    }
}
