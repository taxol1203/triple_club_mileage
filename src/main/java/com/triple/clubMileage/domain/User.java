package com.triple.clubMileage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)", name = "user_id")
    private UUID id;

    private String name;
    private int point;

    @OneToMany(mappedBy = "user")
    private List<History> historyList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    //==생성 메서드==//
    public User createUser(UUID userId, String userName, int p){
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setPoint(p);

        return user;
    }
}
