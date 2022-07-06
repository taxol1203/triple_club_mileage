package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
        if(user.getId() == null){
            em.persist(user);
        }
        else{
            em.merge(user);
        }
    }

    public int getPoint(String id){
        return em.createQuery("select u.point from User u where u.id = :id", Integer.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
