package com.triple.clubMileage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
public class Photo {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "photo_id")
    private UUID id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    //==생성 메서드==//
    public static Photo createPhoto(String photoId, String url){
        Photo photo = new Photo();
        photo.setId(UUID.fromString(photoId));
        photo.setUrl(url);

        return photo;
    }

}