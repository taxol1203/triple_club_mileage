package com.triple.clubMileage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class History {
    @Id @GeneratedValue
    @Column(name = "history_id")
    private Long id;

/*    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID user_id;*/

    private int point;
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getHistoryList().add(this);
    }

    //== 생성 메서드 ==//
    public History createHistory(User user, int point){
        History history = new History();
        history.setUser(user);
        history.setPoint(point);
        history.setCreated_at(LocalDateTime.now());

        return history;
    }
}
