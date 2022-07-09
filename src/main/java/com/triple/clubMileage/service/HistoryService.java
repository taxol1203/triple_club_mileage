package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.HistoryRepository;
import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
