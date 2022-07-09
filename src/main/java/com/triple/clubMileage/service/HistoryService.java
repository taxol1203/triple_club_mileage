package com.triple.clubMileage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.triple.clubMileage.Repository.HistoryRepository;
import com.triple.clubMileage.controller.HistoryDTO;
import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public ResponseEntity<?> getHistoryList(User user) throws JsonProcessingException {
        Optional<List<History>> historyListOp = historyRepository.findByUser(user);
        List<History> historyList = historyListOp.get();

        List<HistoryDTO> historyDTOList = new ArrayList<>();
        for(History history : historyList){
            HistoryDTO historyDTO = new HistoryDTO().createHistoryDto(history.getId(), history.getPoint(), history.getCreated_at(), history.getUser().getId().toString());
            historyDTOList.add(historyDTO);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        mapper.registerModule(new JavaTimeModule());
        String str = mapper.writeValueAsString(historyDTOList);

        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    /**
     * 전체 포인트 부여 기록을 가져온다.
     *
     * @return 포인트 부여 기록 List
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllHistoryList() throws JsonProcessingException {
        List<History> historyList = historyRepository.findAll();

        List<HistoryDTO> historyDTOList = new ArrayList<>();
        for(History history : historyList){
            HistoryDTO historyDTO = new HistoryDTO().createHistoryDto(history.getId(), history.getPoint(), history.getCreated_at(), history.getUser().getId().toString());
            historyDTOList.add(historyDTO);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        mapper.registerModule(new JavaTimeModule());
        String str = mapper.writeValueAsString(historyDTOList);

        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
