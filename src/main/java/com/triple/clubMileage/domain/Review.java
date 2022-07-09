package com.triple.clubMileage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
public class Review {
    @Id
    @Column(columnDefinition = "BINARY(16)", name = "review_id")
    private UUID id;

    private String content;
    private boolean isFirst;
    private LocalDateTime created_at;       // 생성 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Photo> photoList = new ArrayList<>();

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getReviewList().add(this);
    }

    public void setPlace(Place place) {
        this.place = place;
        user.getReviewList().add(this);
    }

    public void addPhoto(Photo photo){
        photoList.add(photo);
        photo.setReview(this);
    }

    //==생성 메서드==//
    public static Review creativeReview(String reviewId, User user, Place place, String contentStr, boolean chkFirst, List<Photo> photos){
        Review review = new Review();
        review.setId(UUID.fromString(reviewId));
        review.setUser(user);
        review.setPlace(place);
        for(Photo photo : photos){
            review.addPhoto(photo);
        }
        review.setContent(contentStr);
        review.isFirst = chkFirst;
        review.setCreated_at(LocalDateTime.now());

        return review;
    }
}
