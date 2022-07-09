package com.triple.clubMileage.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class HistoryDTO {
    private Long id;
    private int point;
    private LocalDateTime created_at;
    private String userId;

    //== 생성 메서드 ==//
    public HistoryDTO createHistoryDto(Long id, int point, LocalDateTime localDateTime, String userId){
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setId(id);
        historyDTO.setPoint(point);
        historyDTO.setCreated_at(localDateTime);
        historyDTO.setUserId(userId);

        return historyDTO;
    }
}
