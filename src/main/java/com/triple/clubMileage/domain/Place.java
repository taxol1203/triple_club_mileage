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
public class Place {

    @Id
    @Column(columnDefinition = "BINARY(16)", name = "place_id")
    private UUID id;

    private String name;
    private int review_count;

    @OneToMany(mappedBy = "place")
    private List<Review> reviewList = new ArrayList<>();

    //==생성 메서드==//
    public Place createPlace(UUID placeId, String placeName, int count){
        Place place = new Place();
        place.setId(placeId);
        place.setName(placeName);
        place.setReview_count(count);
        return place;
    }
}
