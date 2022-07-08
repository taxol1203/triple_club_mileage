package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.PlaceRepository;
import com.triple.clubMileage.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceService {

    PlaceRepository placeRepository;

    public Place getPlace(String placeId){
        Optional<Place> placeOptional = placeRepository.findById(UUID.fromString(placeId));
        Place place;
        if(placeOptional.isPresent()){
            place = placeOptional.get();
        } else {
            throw new EntityNotFoundException(
                    "Cant find any place under given placeId");
        }
        return place;
    }
}
